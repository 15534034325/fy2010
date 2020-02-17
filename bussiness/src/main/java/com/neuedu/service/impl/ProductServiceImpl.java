package com.neuedu.service.impl;

import com.neuedu.common.ServerResponse;
import com.neuedu.common.StatusEnum;
import com.neuedu.dao.ProductMapper;
import com.neuedu.pojo.Product;
import com.neuedu.service.ICategoryService;
import com.neuedu.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements IProductService {


    @Autowired
    ICategoryService categoryService;


    @Autowired
    ProductMapper productMapper;


    @Override
    public ServerResponse addorUpdate(Product product) {
         //1.判断商品是否为空
        if (product==null){
            return ServerResponse.serverResponseByFail(StatusEnum.PARAM_NOT_EMPTY.getStatus(),StatusEnum.PARAM_NOT_EMPTY.getDesc());

        }

        //先查询是否有产品的ID号
        Integer productId = product.getId();
        String subImages=product.getSubImages();// 1.png,2.png,3.png
        if(subImages!=null&&subImages.length()>0){
            String mainImage=subImages.split(",")[0];
            product.setMainImage(mainImage);
        }

        if (productId==null){//添加商品
            int insertCount = productMapper.insert(product);

            if (insertCount<=0){
                return ServerResponse.serverResponseByFail(StatusEnum.PRODUCT_ADD_FAIL.getStatus(),StatusEnum.PRODUCT_ADD_FAIL.getDesc());
            }else{
                return ServerResponse.serverResponseBySucess("商品添加成功",null);
            }

        }else {//商品更新

            //1.查询商品
            Product product1 = productMapper.selectByPrimaryKey(product.getId());
            if (product1==null){
                //更新商品不存在
                return ServerResponse.serverResponseByFail(StatusEnum.UPDATE_PRODUCT_NOT_EXISTS.getStatus(),StatusEnum.UPDATE_PRODUCT_NOT_EXISTS.getDesc());
            }

            //更新商品
            int updateCount= productMapper.updteProductByActivate(product);

            if (updateCount<=0){//商品更新失败
                return ServerResponse.serverResponseByFail(StatusEnum.PRODUCT_UPDATE_FAIL.getStatus(),StatusEnum.PRODUCT_UPDATE_FAIL.getDesc());
            }else {
                //商品更新成功
                return ServerResponse.serverResponseBySucess("商品更新成功",null);
            }

        }

    }


    /**
     * 前台-商品检索
     */

    @Override
    public ServerResponse list(Integer categoryId, String keyword, Integer pageNum, Integer pageSize, String orderBy) {

        //step1:判断是否传递了类别（categoryId）和keyword（关键字）
         if(categoryId==-1 &&(keyword==null||keyword.equals(""))){
             //前端没有传递categoryId 和Keyword,往前端返回空的数据

         }



        return null;
    }
}
