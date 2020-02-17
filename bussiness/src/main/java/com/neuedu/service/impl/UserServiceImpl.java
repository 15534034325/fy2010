package com.neuedu.service.impl;

import com.neuedu.common.RoleEnum;
import com.neuedu.common.ServerResponse;
import com.neuedu.common.StatusEnum;
import com.neuedu.dao.UserMapper;
import com.neuedu.pojo.User;
import com.neuedu.service.IUserService;
import com.neuedu.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements IUserService {


    @Autowired
    private UserMapper userMapper;


    @Override
    public ServerResponse register(User user) {
        //1.参数非空判断,是指添加参数不能为空，不存在返回失败的状态，和描述
        if(user==null){
            return ServerResponse.serverResponseByFail(StatusEnum.PARAM_NOT_EMPTY.getStatus(),StatusEnum.PARAM_NOT_EMPTY.getDesc());
        }

        //2.用户名的非空判断 比较两种用户名为空，字符窜为空
        if (user.getUsername()==null||user.getUsername().equals("")){
            return ServerResponse.serverResponseByFail(StatusEnum.USERNAME_NOT_EMPTY.getStatus(),StatusEnum.USERNAME_NOT_EMPTY.getDesc());
        }
        //3.判断密码不能为空  返回调用接口失败的方法
        if (user.getPassword()==null||user.getPassword().equals("")){
            return ServerResponse.serverResponseByFail(StatusEnum.PASSWORD_NOT_EMPTY.getStatus(),StatusEnum.PASSWORD_NOT_EMPTY.getDesc());
        }

        //4.判断用户名是否存在
        int count=userMapper.countUsername(user.getUsername());
        if (count>0){
            //说明用户名已存在   返回用户名已存在的状态和描述
            return ServerResponse.serverResponseByFail(StatusEnum.USERNAME_EXISTS.getStatus(),StatusEnum.USERNAME_EXISTS.getDesc());
        }

        //邮箱非空判断
        if(user.getEmail()==null||user.getEmail().equals("")){
            return ServerResponse.serverResponseByFail(StatusEnum.EMAIL_NOT_EMPTY.getStatus(),StatusEnum.EMAIL_NOT_EMPTY.getDesc());
        }

        //联系方式非空判断
        if (user.getPhone()==null||user.getPhone().equals("")){
            return ServerResponse.serverResponseByFail(StatusEnum.PHONE_NOT_EMPTY.getStatus(),StatusEnum.PHONE_NOT_EMPTY.getDesc());
        }


        //5.对明文的密码加密
        String password= MD5Utils.getMD5Code(user.getPassword());
        user.setPassword(password);

        //设置用户角色，默认普通用户
        user.setRole(RoleEnum.ROLE_USER.getRole());



        int insertcount= userMapper.insert(user);
        if(insertcount==0){
            //注册失败
            return ServerResponse.serverResponseByFail(StatusEnum.REGISTER_FAIL.getStatus(),StatusEnum.REGISTER_FAIL.getDesc());
        }


        return ServerResponse.serverResponseBySucess("校验成功",null);
    }

    @Override
    public ServerResponse loginLogic(String username, String password) {
        //1.参数非空判断
        //用户名的非空判断
        if (username==null||username.equals("")){
            return ServerResponse.serverResponseByFail(StatusEnum.USERNAME_NOT_EMPTY.getStatus(),StatusEnum.USERNAME_NOT_EMPTY.getDesc());
        }
        //密码的非空判断
        if (password==null||password.equals("")){
            return ServerResponse.serverResponseByFail(StatusEnum.PASSWORD_NOT_EMPTY.getStatus(),StatusEnum.PASSWORD_NOT_EMPTY.getDesc());
        }

        //2.判断用户名是否存在
        int count = userMapper.countUsername(username);
        if (count==0){//用户名不存在
           return ServerResponse.serverResponseByFail(StatusEnum.USERNAME_NOT_EXISTS.getStatus(),StatusEnum.USERNAME_NOT_EXISTS.getDesc());
        }
        //对明文密码加密
        String _password=MD5Utils.getMD5Code(password);

         //3.根据用户名和密码查询用户信息   //_password 指的是加密后的密码
        User user=userMapper.findUserByUsernameAndpassword(username, _password);

        if(user==null){
            return ServerResponse.serverResponseByFail(StatusEnum.PASSWORD_INCORRENT.getStatus(),StatusEnum.PASSWORD_INCORRENT.getDesc());
        }

        return ServerResponse.serverResponseBySucess(null,user);
    }





}
