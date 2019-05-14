package com.example.muscishow.controller.img;

import com.example.muscishow.ResultUtils.Result;
import com.example.muscishow.server.CommonServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yuanci
 * @Date: 2019/5/10
 * @Version: 1.0
 * @Description:
 */
@RestController
@RequestMapping("/qiniu")
public class ImgQiNiuUploadController {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    CommonServer commonServer;

    /**
     * 用于七牛上传时，前端需要的token凭证
     * @return
     */
    @RequestMapping("/token")
    public Result token(){
        return commonServer.token();
    }

    /**
     * 覆盖上传的凭证
     * @param key
     * @return
     */
    @RequestMapping("/keytoken")
    public Result keyToken(String key){
        return commonServer.keyToken(key);
    }

    /**
     * 删除七牛上文件
     * @param fileName
     * @return
     */
    @RequestMapping("/delete")
    public Result deleteImg(String fileName){
        return commonServer.deleteImg(fileName);
    }

    /**
     * 获取七牛云上的文件列表
     * @param prefix 文件前缀，充当文件夹作用
     * @return
     */
    @RequestMapping("/filelist")
    public Result getFileList(String prefix){
        return commonServer.getFileList(prefix);
    }

    /**
     * 当对一个文件进行了替换，删除操作时，进行一次CDN强刷，让CDN中的文件刷新缓存
     * @param key
     */
    @RequestMapping("/refresh")
    public void refresh(String key){
        commonServer.refresh(key);
    }
}
