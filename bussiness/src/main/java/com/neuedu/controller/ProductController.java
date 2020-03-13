package com.neuedu.controller;


import com.neuedu.common.Consts;
import com.neuedu.common.RoleEnum;
import com.neuedu.common.ServerResponse;
import com.neuedu.common.StatusEnum;
import com.neuedu.pojo.Product;
import com.neuedu.pojo.User;
import com.neuedu.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/product/")
public class ProductController {


    @Autowired
    IProductService productService;



    @RequestMapping("/save.do")
    public  ServerResponse addOrUpdate(Product product, HttpSession session){
        //先判断用户是否登录
        User user=(User)session.getAttribute(Consts.USER);
        if (user==null){
            return ServerResponse.serverResponseByFail(StatusEnum.NO_LOGIN.getStatus(),StatusEnum.NO_LOGIN.getDesc());
        }
        //只有管理员权限才能添加类别
        if(user.getRole()!= RoleEnum.ROLE_ADMIN.getRole()){//无管理员权限
            return ServerResponse.serverResponseByFail(StatusEnum.NO_AUTHORITY.getStatus(),StatusEnum.NO_AUTHORITY.getDesc());
        }

        ServerResponse serverResponse=productService.addorUpdate(product);

        return serverResponse;

    }









}
