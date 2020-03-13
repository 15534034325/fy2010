package com.neuedu.common;
/**
 * 接口状态码 枚举
 * */
public enum StatusEnum {

    NO_LOGIN(100,"未登录"),
    NO_AUTHORITY(101,"无权限操作"),

    PARAM_NOT_EMPTY(1,"参数不能为空"),
    USERNAME_NOT_EMPTY(2,"用户名不能为空"),
    EMAIL_NOT_EMPTY(3,"邮箱不能为空"),
    PHONE_NOT_EMPTY(4,"联系方式不能为空"),
    QUESTION_NOT_EMPTY(5,"密保问题不能为空"),
    ANSWER_NOT_EMPTY(6,"密保答案不能为空"),
    PASSWORD_NOT_EMPTY(7,"密码不能为空"),

    USERNAME_EXISTS(8,"用户名已经存在"),
    EMAIL_EXISTS(9,"邮箱已经存在"),
    REGISTER_FAIL(10,"注册失败"),
    USERNAME_NOT_EXISTS(11,"用户名不存在"),
    PASSWORD_INCORRENT(12,"密码错误"),
    CATEGORY_NAME_NOT_EMPTY(13,"类别名称不能为空"),
    CATEGORY_INSERT_FAIL(14,"类别添加失败"),
    CATEGORYID_NOT_EMPTY(15,"类别ID不能为空"),
    CATEGORY_NOT_EXISTS(16,"类别不存在"),
    CATEGORY_UPDATE_FAIL(17,"类别修改失败"),

    UPLOAD_FILENAME_NOT_EMPTY(18,"上传的文件名不能为空"),
    PRODUCT_ADD_FAIL(19,"商品添加失败"),
    UPDATE_PRODUCT_NOT_EXISTS(20,"更新的商品不存在"),
    PRODUCT_UPDATE_FAIL(21,"商品更新失败"),

    PRODUCT_OFFLINEORDELETE_FAIL(22,"商品已经下架或删除"),
    UPDATE_PRODUCT_CART_FAIL(24,"更新购物车商品失败"),
    PRODUCT_ADD_CART_FAIL(23,"商品加入购物车失败"),
    PRODUCT_NOT_EXISTS(25,"商品不存在"),
    ADDRESS_NOT_EMPYT(26,"收货地址不能为空"),


    USER_CART_EMPTY(27,"用户购物车为空"),
    PRODUCT_STOCK_NOT_FULL(28,"商品库存不足"),

    ORDER_CREATE_FAIL(29,"订单创建失败"),
    ORDER_ITEM_CREATE_FAIL(30,"订单明细创建失败"),
    REDUCE_STOCK_FAIL(31,"商品扣库存失败"),
    CART_CLEAN_FAIL(32,"购物车清空失败"),
    ORDER_NOT_EXISTS(33,"订单不存在"),
    ORDER_NOT_CANCEL(34,"订单无法取消"),
    ORDER_CANCEL_FAIL(35,"订单取消失败"),
    ADDRESS_ADD_FAIL(36,"地址添加失败"),

    ALIPAY_ORDER_FAIL(37,"支付宝预下单失败"),
    ORDER_STATUS_FAIL(38,"订单状态修改失败"),

    ;


    private  int status; //状态码值
    private String desc;//对状态码值的描述

    StatusEnum(int status,String desc){
        this.status=status;
        this.desc=desc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}