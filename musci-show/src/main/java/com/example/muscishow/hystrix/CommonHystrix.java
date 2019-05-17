package com.example.muscishow.hystrix;

import com.example.muscishow.ResultUtils.Result;
import com.example.muscishow.ResultUtils.ResultFactory;
import com.example.muscishow.dto.UserDto;
import com.example.muscishow.model.Comment;
import com.example.muscishow.model.Music;
import com.example.muscishow.model.PageForm;
import com.example.muscishow.model.User;
import com.example.muscishow.server.CommonServer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: yuanci
 * @Date: 2019/4/26
 * @Version: 1.0
 * @Description:
 */
@Component
public class CommonHystrix implements CommonServer {

    public Result initErrResult(Object data){
        if(data == "") data = "您可以重新提交或刷新页面";
        return ResultFactory.buidResult(404,"对不起，服务器开小差了",data);
    }

    @Override
    public Result getMusicList(PageForm pageForm) {
        List<Music> list = null;
        return initErrResult(list);
    }

    @Override
    public Result getmusicSRlist(String pageStart, String rows, String title) {
        List<Music> list = null;
        return initErrResult(list);
    }

    @Override
    public Result tokenExpire() {
        return initErrResult("");
    }

    @Override
    public Result musicCreate(Music music) {
        return initErrResult("");
    }

    @Override
    public Result getMusicDetail(int id) {
        Music music = null;
        return initErrResult(music);
    }

    @Override
    public Result musicModify(int id) {
        Music music = null;
        return initErrResult(music);
    }

    @Override
    public Result musicUpdate(Music music) {
        return initErrResult("");
    }

    @Override
    public Result toComment(Comment comment) {
        return initErrResult("");
    }

    @Override
    public Result removerComment(Integer id) {
        return initErrResult("");
    }

    @Override
    public Result favLink(Integer music_id) {
        return initErrResult("");
    }

    @Override
    public Result favList(Integer page) {
        List<Comment> list = new ArrayList<>();
        return initErrResult(list);
    }

    @Override
    public Result favDelete(Integer music_id) {
        return initErrResult("");
    }

    @Override
    public Result showLogin(User user) {
        return initErrResult("");
    }

    @Override
    public Result showLoginOut() {
        return initErrResult("");
    }

    @Override
    public Result register(User user) {
        return initErrResult("");
    }

    @Override
    public Result updatePassword(UserDto userDto) {
        return initErrResult("");
    }

    @Override
    public Result token() {
        return initErrResult("获取上传凭证失败，请重新选择文件");
    }

    @Override
    public Result keyToken(String key) {
        return initErrResult("获取上传凭证失败，请重新选择文件");
    }

    @Override
    public Result deleteImg(String fileName) {
        return initErrResult("");
    }

    @Override
    public Result getFileList(String prefix) {
        return initErrResult("");
    }

    @Override
    public void refresh(String key) {

    }

    @Override
    public Result getMusicDetail_wx(int id) {
        return initErrResult("");
    }

    @Override
    public Result addFavWx(int id) {
        return initErrResult("");
    }

    @Override
    public Result delete(Integer id) {
        return initErrResult("");
    }

    @Override
    public Result list(Integer page, Integer rows) {
        return initErrResult("");
    }

    @Override
    public Result decodeUserInfo(String encryptedData, String iv, String code) {
        return initErrResult("");
    }

    @Override
    public Result verifyToken() {
        return initErrResult("");
    }

    @Override
    public Result goChat() {
        return initErrResult("");
    }

    @Override
    public Result updateInfo_show(User user) {
        return initErrResult("");
    }

    @Override
    public Object vueLogin_vue(User user) {
        return initErrResult("");
    }

    @Override
    public Result loginOut_vue() {
        return initErrResult("");
    }

    @Override
    public Object getUserList_vue(PageForm pageForm) {
        return initErrResult("");
    }

    @Override
    public Object getUserInfo_vue(String account) {
        return initErrResult("");
    }

    @Override
    public Result updateInfo_vue(User user) {
        return initErrResult("");
    }

    @Override
    public Result musicEidt_vue(Integer id) {
        return initErrResult("");
    }

    @Override
    public Result musicUpdate_vue(Music music) {
        return initErrResult("");
    }

    @Override
    public Result musicList_vue(PageForm pageForm) {
        return initErrResult("");
    }

    @Override
    public Result musicCreate_vue(Music music) {
        return initErrResult("");
    }

    @Override
    public Result musicDelete(Integer id) {
        return initErrResult("");
    }
}
