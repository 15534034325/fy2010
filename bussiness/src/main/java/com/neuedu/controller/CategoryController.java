package com.neuedu.controller;


import com.neuedu.common.Consts;
import com.neuedu.common.RoleEnum;
import com.neuedu.common.ServerResponse;
import com.neuedu.common.StatusEnum;
import com.neuedu.pojo.User;
import com.neuedu.service.ICategoryService;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/category/")
public class CategoryController {

    @Autowired
    ICategoryService categoryService;

    /**
     *
     * @param parentId
     * @param categoryName
     * @param session
     * @return
     * 添加类别
     */
    @RequestMapping("add_category.do")
    public ServerResponse addCategory(@RequestParam(value = "parentId",required = false,defaultValue = "0") Integer parentId,
                                      @RequestParam("categoryName") String categoryName,
                                      HttpSession session) {

        //1.判断用户是否登陆
        User user=(User)session.getAttribute(Consts.USER);
        if (user==null){
            return ServerResponse.serverResponseByFail(StatusEnum.NO_LOGIN.getStatus(),StatusEnum.NO_LOGIN.getDesc());
        }

        //step2:只有管理员权限才能添加类别
        if (user.getRole()!= RoleEnum.ROLE_ADMIN.getRole()){//不是管理员添加失败
            return ServerResponse.serverResponseByFail(StatusEnum.CATEGORY_INSERT_FAIL.getStatus(),StatusEnum.CATEGORY_INSERT_FAIL.getDesc());

        }
        return  categoryService.addCategory(parentId, categoryName);
    }


    /**
     * 修改类别
     * @param categoryId
     * @param categoryName
     * @param session
     * @return
     */
    @RequestMapping("set_category_name.do")
    public ServerResponse set_category_name(@RequestParam("categoryId") Integer categoryId,
                                           @RequestParam("categoryName") String categoryName,
                                            HttpSession session) {


        //1.判断用户是否登陆
        User user =(User) session.getAttribute(Consts.USER);
        if (user==null){//用户未登录
            return ServerResponse.serverResponseByFail(StatusEnum.NO_LOGIN.getStatus(),StatusEnum.NO_LOGIN.getDesc());
        }
        //2.只有 管理员才能修改类别
        if (user.getRole()!=RoleEnum.ROLE_ADMIN.getRole()){//不是管理员修改失败
            return ServerResponse.serverResponseByFail(StatusEnum.NO_AUTHORITY.getStatus(),StatusEnum.NO_AUTHORITY.getDesc());
        }

        return categoryService.set_category_name(categoryId, categoryName);
    }

    /**
     * 获取一级子类别
     * @param categoryId
     * @return
     */
    @RequestMapping("get_category.do")
    public ServerResponse get_category(Integer categoryId){

        return categoryService.get_category(categoryId);


    }

    /**
     *
     * 获取当前分类id及递归子节点categoryId
     * */

    @RequestMapping("get_deep_category.do")
    public ServerResponse get_deep_category(Integer categoryId){

        return categoryService.get_deep_category(categoryId);
    }









}



