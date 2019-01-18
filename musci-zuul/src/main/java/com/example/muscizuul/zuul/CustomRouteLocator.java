package com.example.muscizuul.zuul;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CustomRouteLocator extends SimpleRouteLocator implements RouteLocator {
    public final static Logger log = LoggerFactory.getLogger(CustomRouteLocator.class);

    private JdbcTemplate jdbcTemplate;

    private ZuulProperties properties;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public CustomRouteLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
        this.properties = properties;
        log.info("servletPath:{}",servletPath);
    }

//    //父类已经提供了这个方法，这里写出来只是为了说明这一个方法很重要！！！
    @Override
    protected void doRefresh() {
        doRefresh();
    }

    @Override
    protected Map<String, ZuulProperties.ZuulRoute> locateRoutes() {
        LinkedHashMap<String, ZuulProperties.ZuulRoute> routesMap = new LinkedHashMap<String, ZuulProperties.ZuulRoute>();
        //从application.properties中加载路由信息
        routesMap.putAll(super.locateRoutes());
        //从db中加载路由信息
        routesMap.putAll(locateRoutesFromDB());
        //优化一下配置
        LinkedHashMap<String, ZuulProperties.ZuulRoute> values = new LinkedHashMap<>();
        for(Map.Entry<String, ZuulProperties.ZuulRoute> entry  : routesMap.entrySet()){
            String path = entry.getKey();
            if(!path.startsWith("/")){
                path = "/" + path;
            }
            if(StringUtils.hasText(this.properties.getPrefix())){
                path = this.properties.getPrefix() + path;
                if (!path.startsWith("/"))
                {
                    path = "/" + path;
                }
            }
            values.put(path,entry.getValue());
        }
       return values;
    }

    private Map<String,ZuulProperties.ZuulRoute> locateRoutesFromDB(){
        //导入路径
        Map<String,ZuulProperties.ZuulRoute> routeMap = new LinkedHashMap<>();
        List<ZuulRouteVO> results = jdbcTemplate.query("select * from gateway_api_define where enabled = true ",new BeanPropertyRowMapper<>(ZuulRouteVO.class));
        for(ZuulRouteVO result : results){
            if (org.apache.commons.lang.StringUtils.isBlank(result.getPath())||org.apache.commons.lang.StringUtils.isBlank(result.getUrl())){
              continue;
            }
            ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute();
            try {
                org.springframework.beans.BeanUtils.copyProperties(result,zuulRoute);
            }catch (Exception e){
                log.error("=============load zuul route info from db with error==============",e);
            }
            routeMap.put(zuulRoute.getPath(),zuulRoute);
        }
        return routeMap;
    }

    public static class ZuulRouteVO{
        private String id;
        private String path;
        private String serviceId;
        private String url;
        private boolean stripPrefix = true;
        private Boolean retryable;
        private Boolean enabled;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isStripPrefix() {
            return stripPrefix;
        }

        public void setStripPrefix(boolean stripPrefix) {
            this.stripPrefix = stripPrefix;
        }

        public Boolean getRetryable() {
            return retryable;
        }

        public void setRetryable(Boolean retryable) {
            this.retryable = retryable;
        }

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }
    }

}
