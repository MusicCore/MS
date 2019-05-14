package com.example.musicapi.common.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.musicapi.common.ResultUtils.Result;
import com.example.musicapi.common.ResultUtils.ResultFactory;
import com.example.musicapi.common.model.Security;
import com.example.musicapi.common.until.StringUtils;
import com.example.musicapi.common.vo.ImgAndTotal;
import com.example.musicapi.common.vo.ImgUrl;
import com.qiniu.cdn.CdnManager;
import com.qiniu.cdn.CdnResult;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: yuanci
 * @Date: 2019/5/13
 * @Version: 1.0
 * @Description:
 */
@RestController
@RequestMapping("/qiniu")
public class FileQnAction {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private final static Logger log = LoggerFactory.getLogger(FileQnAction.class);

    @Autowired
    Security security;

    /**
     * 用于七牛上传时，前端需要的token凭证
     * @return
     */
    @PostMapping("/token")
    public Result token(){
        Auth auth = Auth.create(security.getAccessKey(), security.getSecretKey());
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"fsize\":$(fsize)}");
        long expireSeconds = 3600;
        String upToken = auth.uploadToken(security.getBucket(), null, expireSeconds, putPolicy);
        JSONObject obj = new JSONObject();
        log.info("生成上传七牛token："+ upToken);
        obj.put("domain",security.getDomain());
        obj.put("token",upToken);
        obj.put("fileName", StringUtils.UUID());
        return ResultFactory.buildSuccessResult(obj);
    }

    /**
     * 覆盖上传的凭证
     * @param key
     * @return
     */
    @PostMapping("/keytoken")
    public Result keyToken(String key){
        Auth auth = Auth.create(security.getAccessKey(), security.getSecretKey());
        String upToken = auth.uploadToken(security.getBucket(), key);
        log.info("生成覆盖上传七牛token："+ upToken);
        return ResultFactory.buildSuccessResult(upToken);
    }

    /**
     * 删除七牛上文件
     * @param fileName
     * @return
     */
    @PostMapping("/delete")
    public Result deleteImg(String fileName){
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());

        Auth auth = Auth.create(security.getAccessKey(), security.getAccessKey());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(security.getBucket(), fileName);
            log.info("删除文件："+ fileName);
            return ResultFactory.buildSuccessResult("");
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            log.error("删除文件出错code：" + ex.code());
            log.error("删除文件出错：" + ex.response.toString());
            return ResultFactory.buildFailResult("ex.response.toString()");
        }

    }

    public Result getFileInfo(String key){
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());

        Auth auth = Auth.create(security.getAccessKey(), security.getAccessKey());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            FileInfo fileInfo = bucketManager.stat(security.getBucket(), key);
            JSONObject obj = new JSONObject();
            obj.put("size",fileInfo.fsize);
            obj.put("putTime",fileInfo.putTime);
            System.out.println(fileInfo.hash);
            System.out.println(fileInfo.fsize);
            System.out.println(fileInfo.mimeType);
            System.out.println(fileInfo.putTime);
            return ResultFactory.buildSuccessResult(obj);
        } catch (QiniuException ex) {
            System.err.println(ex.response.toString());
            return ResultFactory.buildFailResult("");
        }

    }

    /**
     * 获取七牛云上的文件列表
     * @param prefix 文件前缀，充当文件夹作用
     * @return
     */
    @PostMapping("/filelist")
    public Result getFileList(String prefix){
        Configuration cfg = new Configuration(Zone.zone2());

        Auth auth = Auth.create(security.getAccessKey(), security.getSecretKey());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        ImgAndTotal imgAndTotal = new ImgAndTotal();
        //列举空间文件列表
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(security.getBucket(), prefix);
        log.info("获取文件夹："+ prefix);
        while (fileListIterator.hasNext()) {
            //处理获取的file list结果
            FileInfo[] items = fileListIterator.next();
            List<ImgUrl> list = new ArrayList<>();
            for (FileInfo item : items) {
                ImgUrl imgUrl = new ImgUrl();
                imgUrl.setName(item.key);
                imgUrl.setUrl(security.getDomain()+item.key);
                list.add(imgUrl);
//                System.out.println(item.key);
//                System.out.println(item.hash);
//                System.out.println(item.fsize);
//                System.out.println(item.mimeType);
//                System.out.println(item.putTime);
//                System.out.println(item.endUser);
            }
            imgAndTotal.setImgUrlList(list);
            imgAndTotal.setTotalPages(list.size());
        }
        return ResultFactory.buildSuccessResult(imgAndTotal);
    }

    /**
     * 当对一个文件进行了替换，删除操作时，进行一次CDN强刷，让CDN中的文件刷新缓存
     * @param key
     */
    @RequestMapping("/refresh")
    public void refresh(String key){
        Auth auth = Auth.create(security.getAccessKey(), security.getSecretKey());
        CdnManager c = new CdnManager(auth);

        //待刷新的链接列表
        String[] urls = new String[]{
                security.getDomain()+key,
                //....
        };
        try {
            //单次方法调用刷新的链接不可以超过100个
            CdnResult.RefreshResult result = c.refreshUrls(urls);
            log.info("对CDN中'"+key +"'进行刷新");
//            System.out.println(result.code);
            //获取其他的回复内容
        } catch (QiniuException e) {
//            System.err.println(e.response.toString());
        }

    }
}
