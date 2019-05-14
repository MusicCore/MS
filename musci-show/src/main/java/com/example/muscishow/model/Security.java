package com.example.muscishow.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "music")
public class Security {
    //    服务器域名
    private String domain;
    //    本地测试域名
    private String test_domain;

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
