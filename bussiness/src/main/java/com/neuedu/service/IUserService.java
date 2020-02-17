package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


public interface IUserService {

    /**
     * 注册接口
     * @param user
     * @return ServerResponse
     */

    public ServerResponse register(User user);

    /**
     * 处理登录业务逻辑
     * */
    ServerResponse loginLogic(String username,String password);


}
