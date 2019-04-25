package com.example.muscishow.controller.img;

import com.example.muscishow.ResultUtils.Result;
import com.example.muscishow.ResultUtils.ResultFactory;
import com.example.muscishow.model.Security;
import com.example.muscishow.vo.ImgAndTotal;
import com.example.muscishow.vo.ImgUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @Author: yuanci
 * @Date: 2019/3/7
 * @Version: 1.0
 * @Description:
 */
@Controller
public class ImgManageController {

    @Autowired
    Security security;

    private final static Logger log = LoggerFactory.getLogger(ImgManageController.class);
    //    测试环境
    public static final String IMG_PATH_test = ClassUtils.getDefaultClassLoader().getResource("").getPath()+ File.separator + "static" + File.separator + "upload" + File.separator + "img";
    public static final String BKIMG_PATH_test = ClassUtils.getDefaultClassLoader().getResource("").getPath()+ File.separator + "static" + File.separator + "upload" + File.separator + "bgImg";
    //    正式环境
    public static final String IMG_PATH =  "/root/upload/img/";
    public static final String BKIMG_PATH =  "/root/upload/bgImg/";
    //    分页一页多少个图片（为适应bootstrap的分页
    private static final int ROWS = 6;

    @RequestMapping("/imgmanage")
    public String toImgManage(){
        return "imgmanage.html";
    }

    @RequestMapping("/imgmanage/show")
    @ResponseBody
    public Result showImg(int page){
        ImgAndTotal files = getImgList(IMG_PATH_test,security.getTest_domain()+"img/",page);
        return ResultFactory.buildSuccessResult(files);
    }

    @PostMapping("/imgmanage/delete")
    @ResponseBody
    public Result imgDel(String fileName){
        File file = new File(IMG_PATH_test+File.separator+fileName);
        if(file.exists()){
            file.delete();
            log.info("\n删除"+fileName+"文件\n");
        }
        return ResultFactory.buildSuccessResult(fileName+"已删除！");
    }

    @RequestMapping("/bkimgmanage")
    public String toBkImgManage(){
        return "bkimgmanage.html";
    }

    @RequestMapping("/imgmanage_bk/show")
    @ResponseBody
    public Result showbkImg(){
        ImgAndTotal files = getImgList(IMG_PATH_test,security.getTest_domain()+"bgImg/",1);
        return ResultFactory.buildSuccessResult(files);
    }

    @RequestMapping("/imgmanage_bk/change")
    @ResponseBody
    public Result changeBkImg(MultipartFile file,String name){
        File file_origin = new File(IMG_PATH_test+File.separator + name);
        if(file_origin.exists()){
            file_origin.delete();
            log.info("\n替换之前删除"+name+"文件\n");
        }
        try{
            file.transferTo(file_origin);
            log.info("\n替换"+file_origin+"文件\n");
            return ResultFactory.buildSuccessResult("");
        }catch (Exception e){
            log.error("上传出错："+e);
            return ResultFactory.buildFailResult("上传出错");
        }
    }

    /**
     * 先给所有文件排序然后通过页数生成html所需要的img格式链接以及有多少页数
     * @param path
     * @param dirs
     * @param page
     * @return
     */
    public ImgAndTotal getImgList(String path,String dirs,int page){
        File fileUrl = new File(path);
        log.info("\n取得路径"+path+"文件夹的文件\n");
        File [] fs = fileUrl.listFiles();
        //最后修改时间从新往旧排
        Arrays.sort(fs,new Comparator< File>(){
            public int compare(File f1, File f2) {
                long diff = f1.lastModified() - f2.lastModified();
                if (diff > 0)
                    return -1;
                else if (diff == 0)
                    return 0;
                else
                    return 1;
            }
            public boolean equals(Object obj) {
                return true;
            }

        });
        int totalPages;
        if((fs.length % ROWS) > 0) {
            totalPages = fs.length / ROWS + 1;
        }else {
            totalPages = fs.length / ROWS;
        }
        List<ImgUrl> imgUrlList = new ArrayList<>();
        int pageStart = (page-1) * ROWS;
        int pageOver ;
        if ((fs.length - pageStart) < ROWS){
            pageOver = fs.length -1;
        }else{
            pageOver = pageStart + ROWS -1;
        }
        for (int i = pageStart; i <= pageOver; i++) {
//            System.out.println("当前:" + i);
            File file = fs[i];
//            System.out.println("最后修改时间:" + new Date(file.lastModified()));
            ImgUrl imgUrl = new ImgUrl(file.getName(),dirs+file.getName(),"");
            imgUrlList.add(imgUrl);
        }
        ImgAndTotal imgAndTotal = new ImgAndTotal(imgUrlList,totalPages);
        return imgAndTotal;
    }
}
