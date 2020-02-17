package com.neuedu.service;


import com.neuedu.common.ServerResponse;

/*
类别模块处理逻辑
 */
public interface ICategoryService {


    /**
     *添加类别
     */

    public ServerResponse addCategory(Integer parentId, String categoryName);


    /**
     * 修改类别
     * */

    ServerResponse set_category_name(Integer categoryId,String categoryName);

    /**
     * 查询平级子节点
     */

    ServerResponse get_category(Integer categoryId);

    /**
     * 递归查询子节点
     */
    ServerResponse get_deep_category(Integer categoryId);




}
