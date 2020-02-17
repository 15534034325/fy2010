package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Product;

public interface IProductService {

    /**
     * 添加或修改产品
     * @param product
     * @return
     */
    ServerResponse addorUpdate(Product product);


    /**
     * 前台-商品检索
     */

    ServerResponse list(Integer categoryId, String keyword, Integer pageNum, Integer pageSize, String orderBy);



}
