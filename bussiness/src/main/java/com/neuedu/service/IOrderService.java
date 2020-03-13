package com.neuedu.service;

import com.neuedu.common.ServerResponse;

public interface IOrderService {


    //默认的接口是public
    /**
     * 创建订单(根据用户id，地址id)
     *
     */
    ServerResponse createOrder(Integer userId,Integer shippingId);


    /**
     *
     * 取消订单
     * */
    ServerResponse cancelOrder(Long orderNo);


    /**
     * 根据订单号查询订单信息
     * */
    ServerResponse findOrderByOrderNo(Long orderNo);

    /**
     * 支付成功后 修改订单状态
     */
    ServerResponse updateOrder(Long orderNo,String payTime,Integer orderStatus);






}
