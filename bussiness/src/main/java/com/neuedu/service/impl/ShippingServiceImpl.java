package com.neuedu.service.impl;

import com.neuedu.common.ServerResponse;
import com.neuedu.common.StatusEnum;
import com.neuedu.dao.ShippingMapper;
import com.neuedu.pojo.Shipping;
import com.neuedu.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShippingServiceImpl implements IShippingService {


    @Autowired
    ShippingMapper shippingMapper;



    @Override
    public ServerResponse add(Shipping shipping) {
        if (shipping==null){
            return ServerResponse.serverResponseByFail(StatusEnum.ADDRESS_NOT_EMPYT.getStatus(),StatusEnum.ADDRESS_NOT_EMPYT.getDesc());
        }

        int count=shippingMapper.insert(shipping);

        if(count<=0){
            return ServerResponse.serverResponseByFail(StatusEnum.ADDRESS_ADD_FAIL.getStatus(),StatusEnum.ADDRESS_ADD_FAIL.getDesc());
        }

        return ServerResponse.serverResponseBySucess(null,shipping.getId());// STOPSHIP: 20/2/25
    }





}
