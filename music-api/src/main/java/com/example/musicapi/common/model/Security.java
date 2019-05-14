package com.example.musicapi.common.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "music")
public class Security {
    //    开关
    private String enable;
    //    允许地址
    private String allowUrl;
    //    用户允许功能
    private String loginAllows;
    //    服务器域名
    private String domain;
    //    本地测试域名
    private String test_domain;
    private String wxspAppid;
    private String wxspSecret;

    private String accessKey;

    private String secretKey;

    private String bucket;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getWxspAppid() {
        return wxspAppid;
    }

    public void setWxspAppid(String wxspAppid) {
        this.wxspAppid = wxspAppid;
    }

    public String getWxspSecret() {
        return wxspSecret;
    }

    public void setWxspSecret(String wxspSecret) {
        this.wxspSecret = wxspSecret;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getAllowUrl() {
        return allowUrl;
    }

    public void setAllowUrl(String allowUrl) {
        this.allowUrl = allowUrl;
    }

    public String getLoginAllows() {
        return loginAllows;
    }

    public void setLoginAllows(String loginAllows) {
        this.loginAllows = loginAllows;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getTest_domain() {
        return test_domain;
    }

    public void setTest_domain(String test_domain) {
        this.test_domain = test_domain;
    }
}
