package com.neuedu.controller;


import com.neuedu.common.ServerResponse;
import com.neuedu.common.StatusEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


/*
这里要访问一个页面所以要用controller


 */
@Controller
public class UploadController {




    @Value("${upload.path}")
    private String uploadPath;

    @RequestMapping(value = "/upload",method = RequestMethod.GET)
    public String upload(){
        return "upload";
    }


    /**
     * 图片上传
     *
     * file  待上传的文件
     */


    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse upload( @RequestParam("uploadfile") MultipartFile file){

        if(file==null){
            return null;
        }

        //1.获取文件的名称 xxx.png
        String filename = file.getOriginalFilename();

        if(filename==null){
            return ServerResponse.serverResponseByFail(StatusEnum.UPLOAD_FILENAME_NOT_EMPTY.getStatus(),StatusEnum.UPLOAD_FILENAME_NOT_EMPTY.getDesc());
        }


        //2.获取文件的扩展名
        String ext = filename.substring(filename.lastIndexOf("."));
        //3.重新命名，为文件生成一个唯一的名称
        String name = UUID.randomUUID().toString();
        //4.新的文件名
        String newFilename=name+ext;


        //创建保存文件的目录
        File target = new File(uploadPath);
        if(!target.exists()){
            target.mkdirs();
        }
        //5.创建文件
        File newFile = new File(uploadPath,newFilename);


        try {
            //6.将文件写入到磁盘
            file.transferTo(newFile);

            //返回前端
            return ServerResponse.serverResponseBySucess(null,newFilename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



}
