package com.neuedu.vo;

import java.math.BigDecimal;
import java.util.List;

public class CartVO {

    private boolean allChecked;//是否全选
    private BigDecimal cartTotalPrice;//购物车总价格

    private List<CartProductVO> cartProductVOList;

    public boolean isAllChecked() {
        return allChecked;
    }

    public void setAllChecked(boolean allChecked) {
        this.allChecked = allChecked;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public List<CartProductVO> getCartProductVOList() {
        return cartProductVOList;
    }

    public void setCartProductVOList(List<CartProductVO> cartProductVOList) {
        this.cartProductVOList = cartProductVOList;
    }
}
