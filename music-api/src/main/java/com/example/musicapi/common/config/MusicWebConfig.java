package com.example.musicapi.common.config;


import com.example.musicapi.common.filter.SecurityFilter;
import com.example.musicapi.common.model.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 拦截器配置
 */
@Configuration
public class MusicWebConfig extends WebMvcConfigurationSupport  {
    @Autowired
    SecurityFilter securityFilter;
    @Autowired
    Security security;
//    设定默认访问页面
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }
    /**
     * 添加静态资源文件，外部可以直接访问地址
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //需要配置1：----------- 需要告知系统，这是要被当成静态文件的！
        //第一个方法设置访问路径前缀，第二个方法设置资源路径
//        registry.addResourceHandler("/static/**").addResourceLocations("file:/root/upload/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/upload/**").addResourceLocations("file:/root/upload/");
//        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/webjars/");
        super.addResourceHandlers(registry);
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * 注册安全拦截器
         */
        FilterRegistry(registry);
    }

    /**
     * 注册拦截器 可多注册
     * @param registry
     */
    public void FilterRegistry(InterceptorRegistry registry){
        if(security.getEnable().equals("Y")){
            //      数组初始化
            String []securityFilterStr = new String[]{};
//        获取允许地址
            String allowUrl = security.getAllowUrl();
            if (!StringUtils.isEmpty(allowUrl)){
                securityFilterStr = allowUrl.split(",");
            }
//        注册
//        addPathPatterns 用于添加拦截规则
//        excludePathPatterns 用户排除拦截
            registry.addInterceptor(securityFilter).addPathPatterns("/**").excludePathPatterns(securityFilterStr);
        }
    }
}
