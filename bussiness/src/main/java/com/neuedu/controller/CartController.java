package com.neuedu.controller;


import com.neuedu.common.Consts;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.User;
import com.neuedu.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/cart/")
public class CartController {

    @Autowired
    ICartService cartService;

    /**
     * 购物车列表
     * */


    @RequestMapping("list.do")
    public ServerResponse list(HttpSession session){

        //1.判断用户是否登录
        User user=(User) session.getAttribute(Consts.USER);
        //2.返回购物车列表
        return cartService.list(user.getId());
    }


    @RequestMapping("add.do")
    public ServerResponse add(Integer productId,Integer count,HttpSession session){
        //1.判断用户是否登陆
        User user=(User) session.getAttribute(Consts.USER);
        //返回添加商品
        return cartService.add(user.getId(),productId,count);
    }


    /**
     * 根据用户id查询已选中的购物车商品
     */

    @RequestMapping("select.do")
    public ServerResponse check(Integer userId,HttpSession session){
        //1.判断用户是否登陆
       User user= (User)session.getAttribute(Consts.USER);
        return cartService.findCartByUserIdAndChecked(user.getId());
    }

}
