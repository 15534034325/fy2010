package com.neuedu.service.impl;

import com.neuedu.common.Consts;
import com.neuedu.common.ServerResponse;
import com.neuedu.common.StatusEnum;
import com.neuedu.dao.CartMapper;
import com.neuedu.exception.BusinessException;
import com.neuedu.pojo.Cart;
import com.neuedu.service.ICartService;
import com.neuedu.service.IProductService;
import com.neuedu.util.BigDecimalUtil;
import com.neuedu.vo.CartProductVO;
import com.neuedu.vo.CartVO;
import com.neuedu.vo.ProductDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {



    @Autowired
    CartMapper cartMapper;

    @Autowired
    IProductService productService;

    /**
     * 查询购物车列表
     * @param userId
     * @return
     */

    @Override
    public ServerResponse list(Integer userId) {
        CartVO cartVO=getCartVO(userId);

        return ServerResponse.serverResponseBySucess(null,cartVO);
    }


    /**
     * 向购车里添加商品
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    @Override
    public ServerResponse add(Integer userId, Integer productId, Integer count) {
        //step1:参数非空判断
        if(productId==null|| count==null){
            return ServerResponse.serverResponseByFail(StatusEnum.PARAM_NOT_EMPTY.getStatus(),StatusEnum.PARAM_NOT_EMPTY.getDesc());
        }

        //step2:根据productId查询商品是否存在
        ServerResponse<ProductDetailVO> serverResponse=productService.detail(productId);
        if(!serverResponse.isSucess()){
            return ServerResponse.serverResponseByFail(StatusEnum.PRODUCT_OFFLINEORDELETE_FAIL.getStatus(),StatusEnum.PRODUCT_OFFLINEORDELETE_FAIL.getDesc());
        }

        //step3:根据userId和productId去购物车中查询
        Cart cart=cartMapper.findCartByUseridAndProductId(userId, productId);

        if(cart==null){
            //该商品第一次加入到购物车   该商品不在购物车里
            Cart newCart=new Cart();
            newCart.setUserId(userId);
            newCart.setChecked(Consts.CartProductCheckEnum.CHECKED.getCheck());
            newCart.setProductId(productId);
            newCart.setQuantity(count);

            int result= cartMapper.insert(newCart);
            if(result>0){
                //添加成功
                return ServerResponse.serverResponseBySucess(null,getCartVO(userId));
            }else{
                //加入购物车失败
                return ServerResponse.serverResponseByFail(StatusEnum.PRODUCT_ADD_CART_FAIL.getStatus(),StatusEnum.PRODUCT_ADD_CART_FAIL.getDesc());
            }

        }else{
            //该商品已经在购物车中

            cart.setQuantity(cart.getQuantity()+count);
            int result=cartMapper.updateByPrimaryKey(cart);
            if(result>0){
                //更新成功
                return ServerResponse.serverResponseBySucess(null,getCartVO(userId));
            }else{
                //更新购物车商品失败
                return ServerResponse.serverResponseByFail(StatusEnum.UPDATE_PRODUCT_CART_FAIL.getStatus(),StatusEnum.UPDATE_PRODUCT_CART_FAIL.getDesc());
            }

        }
    }

    /*
    查询购物车中用户已经选中的商品(和订单相关)
     */
    @Override
    public ServerResponse findCartByUserIdAndChecked(Integer userId) {
        if(userId==null){
            return ServerResponse.serverResponseByFail(StatusEnum.NO_LOGIN.getStatus(),StatusEnum.NO_LOGIN.getDesc());
        }

        List<Cart> cartList= cartMapper.findCartByUseridAndChecked(userId);

        return ServerResponse.serverResponseBySucess(null,cartList);
    }

    /**
     * 批量删除购物车商品
     * @param cartList
     * @return
     */
    @Override
    public ServerResponse deleteBatchByIds(List<Cart> cartList) {
        if(cartList==null|| cartList.size()==0){
            return ServerResponse.serverResponseByFail(StatusEnum.PARAM_NOT_EMPTY.getStatus(),StatusEnum.PARAM_NOT_EMPTY.getDesc());
        }

        int count=cartMapper.deleteBatch(cartList);
        if(count!=cartList.size()){
            throw new BusinessException(StatusEnum.CART_CLEAN_FAIL.getStatus(),StatusEnum.CART_CLEAN_FAIL.getDesc());
        }

        return ServerResponse.serverResponseBySucess();
    }




    private CartVO getCartVO(Integer userId) {
        CartVO cartVO = new CartVO();
        //step1:根据用户id获取购物车信息 -->List<Cart>
        List<Cart> cartList=cartMapper.findCartByUserid(userId);
        //如果够物信息不存在
        if (cartList==null||cartList.size()==0){
            return cartVO;
        }
        //step2: 将List<Cart> ---> List<CartProductVO>
        List<CartProductVO> cartProductVOList=new ArrayList<>();

        BigDecimal cartTotalPrice=new BigDecimal("0");
        for(Cart cart:cartList) {

            CartProductVO cartProductVO = new CartProductVO();

            cartProductVO.setId(cart.getId());
            cartProductVO.setProductId(cart.getProductId());
            cartProductVO.setProductChecked(cart.getChecked());
            cartProductVO.setUserId(userId);

            //根据productId查询商品信息

            ServerResponse<ProductDetailVO> serverResponse=  productService.detail(cart.getProductId());

            if(!serverResponse.isSucess()){
                //商品删除或则下架
                continue;
            }

            ProductDetailVO productDetailVO=  serverResponse.getData();

            cartProductVO.setProductMainImage(productDetailVO.getMainImage());
            cartProductVO.setProductName(productDetailVO.getName());
            cartProductVO.setProductPrice(productDetailVO.getPrice());
            cartProductVO.setProductStatus(productDetailVO.getStatus());
            cartProductVO.setProductStock(productDetailVO.getStock());
            cartProductVO.setProductSubtitle(productDetailVO.getSubtitle());

            Integer quantityInCart=cart.getQuantity();
            Integer realStock= productDetailVO.getStock();

            if(realStock>=quantityInCart){
                //库存充足
                cartProductVO.setLimitQuantity("LIMIT_NUM_SUCCESS");
                cartProductVO.setQuantity(cart.getQuantity());
            }else{
                //库存不足
                //修改该商品在购物车中的数量，根据商品id修改购物车的qualtity
                int count= cartMapper.updateQualtityByProductId(cartProductVO.getProductId(),realStock);

                if(count==0){
                    //修改失败
                    continue;
                }

                cartProductVO.setLimitQuantity("LIMIT_NUM_FAIL");
                cartProductVO.setQuantity(realStock);
            }



            //价格计算
            cartProductVO.setProductTotalPrice(BigDecimalUtil.multi(String.valueOf(productDetailVO.getPrice().doubleValue()),String.valueOf(cartProductVO.getQuantity())));

            if(cartProductVO.getProductChecked()== Consts.CartProductCheckEnum.CHECKED.getCheck()){
                //已选中
                cartTotalPrice=  BigDecimalUtil.add(String.valueOf(cartTotalPrice.doubleValue()),
                        String.valueOf(cartProductVO.getProductTotalPrice().doubleValue()));
            }


            cartProductVOList.add(cartProductVO);

        }

        cartVO.setCartProductVOList(cartProductVOList);


        //step3: 计算购物车总价格

        cartVO.setCartTotalPrice(cartTotalPrice);


        //step4:验证是否全选
        int result=cartMapper.totalCountByUnchecked(userId);


        if(result>0){
            //说明购物车中含有未选中的商品
            cartVO.setAllChecked(false);
        }else{
            cartVO.setAllChecked(true);
        }




        return cartVO;
    }









}
