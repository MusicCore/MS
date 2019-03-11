package com.example.muscimanger.controller;

import com.example.muscimanger.model.Security;
import com.example.muscimanger.until.Result;
import com.example.muscimanger.until.ResultFactory;
import com.example.muscimanger.until.StringUtils;
import com.example.muscimanger.vo.ImgUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * @Author: yuanci
 * @Date: 2018/12/25 13:47
 * @Version 1.0
 */
@RestController
@RequestMapping("/api")
public class ImgUploadController {

    private final static Logger log = LoggerFactory.getLogger(ImgUploadController.class);

    @Autowired
    Security security;

    @RequestMapping("/imgupload")
    public Result imgupload(@RequestBody MultipartFile file, HttpServletRequest request ,
                            String uid){
        // uid是vue上传图片返回确认是否上传成功的id
        log.info("\n-------------------Method : 上传图片start--------------------\n");
        //取得upload文件夹路径
//        String path = request.getSession().getServletContext().getRealPath("/upload");
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath()+ File.separator + "static" + File.separator + "upload" + File.separator + "img";
//        String path = "/root/upload/img/";
        File nfile = new File(path);
        if(!nfile.exists() && !nfile.isDirectory()){
            nfile.mkdirs();
            log.info("初始化上传图片目录"+ path);
        }

        String originalFileName = file.getOriginalFilename();
        log.info("初始图片文件名称：" + originalFileName);
        String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
//        logger.info("文件类型为：" + type);
        //生成不重复的名称
        String fileName = StringUtils.UUID() + "." + type ;
        log.info("生成图片文件名："+fileName);
        File targerFile = new File(path,fileName);

        try {
            file.transferTo(targerFile);
            String url = initUrl("img/",fileName);
//            String url = security.getDomain() + "img/" +fileName;
            ImgUrl imgUrl = new ImgUrl(fileName,url,uid);
            log.info("生成路径名："+ url);
            log.info("\n-------------------Method : 上传图片over--------------------\n");
            return ResultFactory.buildSuccessResult(imgUrl);
        }catch (Exception e){
            e.printStackTrace();
            log.error("出错："+ e);
            return ResultFactory.buildFailResult("上传失败");
        }
    }

    @RequestMapping("/musicupload")
    public Result musicupload(@RequestBody MultipartFile file, HttpServletRequest request ,
                              String uid){
        // uid是vue上传图片返回确认是否上传成功的id
        log.info("\n-------------------Method : 上传音乐start--------------------\n");
//        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath()+ File.separator + "static" + File.separator + "upload"+ File.separator + "music";
        String path = "/root/upload/music/";
        File nfile = new File(path);
        if(!nfile.exists() && !nfile.isDirectory()){
            nfile.mkdirs();
            log.info("初始化上传音乐目录:"+ path);
        }

        String originalFileName = file.getOriginalFilename();
        log.info("初始音乐文件名称：" + originalFileName);
        String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        String fileName = StringUtils.UUID() + "." + type ;
        log.info("生成音乐文件名："+fileName);
        File targerFile = new File(path,fileName);

        try {
            file.transferTo(targerFile);
            String url = initUrl("music/",fileName);
//            String url = security.getDomain() + "music/"+ fileName;
            ImgUrl imgUrl = new ImgUrl(fileName,url,uid);
            log.info("生成路径名："+ url);
            log.info("\n-------------------Method : 上传音乐over--------------------\n");
            return ResultFactory.buildSuccessResult(imgUrl);
        }catch (Exception e){
            e.printStackTrace();
            log.error("出错："+ e);
            return ResultFactory.buildFailResult("上传失败");
        }
    }

    public String initUrl(String s1,String s2){
        String domain;
        do{
            domain = security.getDomain();
        }while (domain == null || "" == domain);
        String url = domain + s1 + s2;
        return url;
    }
}
