package com.example.muscishow.server;

import com.example.muscishow.ResultUtils.Result;
import com.example.muscishow.dto.UserDto;
import com.example.muscishow.hystrix.CommonHystrix;
import com.example.muscishow.model.Comment;
import com.example.muscishow.model.Music;
import com.example.muscishow.model.PageForm;
import com.example.muscishow.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: yuanci
 * @Date: 2019/4/23
 * @Version: 1.0
 * @Description:  @FeignClient不能存在相同的name值，boot2.1.0禁止覆盖重复的bean,若要分开写，在applicaton.properties添加
 *                spring.main.allow-bean-definition-overriding=true
 */
@FeignClient(name = "music-producer",fallback = CommonHystrix.class)
@Component
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

    /**
     * chat
     * @return
     */
    @PostMapping(value = "/show/webScoket")
    public Result goChat();
    /**
     * 上传的凭证
     * @return
     */
    @PostMapping("/qiniu/token")
    public Result token();

    /**
     * 覆盖上传的凭证
     * @param key
     * @return
     */
    @PostMapping("/qiniu/keytoken")
    public Result keyToken(@RequestParam("key") String key);

    /**
     * 删除七牛上文件
     * @param fileName
     * @return
     */
    @PostMapping("/qiniu/delete")
    public Result deleteImg(@RequestParam("fileName") String fileName);

    /**
     * 获取七牛云上的文件列表
     * @param prefix 文件前缀，充当文件夹作用
     * @return
     */
    @PostMapping("/qiniu/filelist")
    public Result getFileList(@RequestParam("prefix") String prefix);

    /**
     * 当对一个文件进行了替换，删除操作时，进行一次CDN强刷，让CDN中的文件刷新缓存
     * @param key
     */
    @RequestMapping("/qiniu/refresh")
    public void refresh(@RequestParam("key") String key);

    /**
     * 微信得到详细的谱子信息
     * @param id 乐谱ID
     * @return
     */
    @PostMapping(value = "api/weixin/musicdetail")
    public Result getMusicDetail_wx(@RequestParam("id") int id);

    /**
     * 微信用户添加收藏夹
     * @param id 乐谱ID
     * @return
     */
    @PostMapping(value = "api/weixin/fav/link")
    public Result addFavWx(@RequestParam("id") int id);

    /**
     * 微信用户删除收藏夹
     * @param id 乐谱ID
     * @return
     */
    @PostMapping(value="api/weixin/favorites/remove")
    public Result delete(@RequestParam("id") Integer id);

    /**
     * 查询收藏夹
     * @param page
     * @return
     */
    @PostMapping(value="api/weixin/favorites/list")
    public Result list(@RequestParam("page") Integer page,@RequestParam("rows") Integer rows);

    /**
     * 生成token,取得openid unionId
     * @param encryptedData
     * @param iv
     * @param code
     * @return
     */
    @PostMapping(value = "api/weixin/decodeUserInfo")
    public Result decodeUserInfo(@RequestParam("encryptedData") String encryptedData,
                                 @RequestParam("iv") String iv, @RequestParam("code") String code);

    /**
     * 验证小程序的token是否过期
     * @return
     */
    @PostMapping(value = "api/weixin/verifytoken")
    public Result verifyToken();
}
