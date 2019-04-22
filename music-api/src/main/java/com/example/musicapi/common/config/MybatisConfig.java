package com.example.musicapi.common.config;

import com.example.musicapi.common.override.MapWrapperFactory;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;

//@Configuration
public class MybatisConfig {
//    替换下以前默认的实现
//    返回一个 ConfigurationCustomizer 的bean,通过匿名内部类实现覆盖默认的MapWrapper的findProperty函数
    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomizer(){
        return new ConfigurationCustomizer() {
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                configuration.setObjectWrapperFactory(new MapWrapperFactory());
            }
        };
    }
}
