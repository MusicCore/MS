package com.example.muscishow.server;

import com.example.muscishow.ResultUtils.Result;
import com.example.muscishow.dto.UserDto;
import com.example.muscishow.model.Comment;
import com.example.muscishow.model.Music;
import com.example.muscishow.model.PageForm;
import com.example.muscishow.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: yuanci
 * @Date: 2019/4/23
 * @Version: 1.0
 * @Description:  @FeignClient不能存在相同的name值，boot2.1.0禁止覆盖重复的bean,若要分开写，在applicaton.properties添加
 *                spring.main.allow-bean-definition-overriding=true
 */
@FeignClient(name = "music-producer")
public interface CommonServer {

    /**
     * show,vue,weChat通用的乐谱列表 yes
     */
    @PostMapping(value = "/music/musiclist")
    public Result getMusicList(@RequestBody PageForm pageForm);

    /**
     * show,weChat通用的查询乐谱列表 yes
     */
    @PostMapping(value = "/music/musicSRlist")
    public Result getmusicSRlist(@RequestParam("pageStart") String pageStart,@RequestParam("rows") String rows,
                                 @RequestParam("title") String title);

    /**
     * 统一token过期处理
     * @return
     */
    @RequestMapping(value = "/token_expire")
    public Result tokenExpire();

    /**
     * show插入信息
     */
    @PostMapping(value = "/show/create")
    public Result musicCreate(@RequestBody Music music);

    /**
     * show 谱子详情页面 yes
     * @param id
     * @return
     */
    @PostMapping(value = "/show/musicdetail")
    public Result getMusicDetail(@RequestParam("id") int id);

    /**
     * show 获取修改页面信息 yes
     * @param id
     * @return
     */
    @PostMapping(value = "/show/modify")
    public Result musicModify(@RequestParam("id") int id);

    /**
     * show 更新曲谱 yes
     * @param music
     * @return
     */
    @PostMapping(value = "/show/musicupdate")
    public Result musicUpdate(@RequestBody Music music);

    /**
     * show 添加评论 yes
     * @param comment
     * @return
     */
    @PostMapping(value="/show/comment/comment")
    public Result toComment(@RequestBody Comment comment);

    /**
     * show 移除评论 yes
     * @param id
     * @return
     */
    @PostMapping(value="/show/comment/rComment")
    public Result removerComment(@RequestParam("id") Integer id);

    /**
     * show 关联收藏夹 yes
     * @param music_id
     * @return
     */
    @PostMapping(value="/show/favorites/link")
    public Result favLink(@RequestParam(value = "music_id") Integer music_id);

    /**
     * show查询收藏夹 yes
     * @param page
     * @return
     */
    @PostMapping(value="/show/favorites/list")
    public Result favList(@RequestParam("page") Integer page);

    /**
     * show删除收藏夹 yes
     * @param music_id
     * @return
     */
    @PostMapping(value="/show/favorites/remove")
    public Result favDelete(@RequestParam("music_id") Integer music_id);

    /**
     *  show登入 yes
     * @param user
     * @param
     * @return
     */
    @PostMapping(value = "/show/login")
    public Result showLogin(@RequestBody User user);

    /**
     * show登出 yes
     * @param
     * @return
     */
    @PostMapping(value = "/show/logout")
    public Result showLoginOut();

    /**
     * show注册
     * @param user
     * @return
     */
    @PostMapping(value = "/show/register")
    public Result register(@RequestBody User user);

    /**
     *  show更新 暂未使用
     * @param user
     * @return
     */
    @PostMapping(value = "/show/userupdate")
    public Result updateInfo(@RequestBody User user);

    /**
     * show修改密码 暂未使用
     * @return
     */
    @PostMapping(value = "/show/updatePwd")
    public Result updatePassword(@RequestBody UserDto userDto);

}
