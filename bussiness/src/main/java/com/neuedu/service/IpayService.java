package com.neuedu.service;

import com.neuedu.common.ServerResponse;

import java.util.Map;

public interface IpayService {

    ServerResponse pay(Long orderNo);

    String callbackLogic(Map<String,String> signParam);

}
