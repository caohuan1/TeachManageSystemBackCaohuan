package com.itheima.config;

import com.itheima.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //配置类
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginCheckInterceptor loginCheckInterceptor;
    
    /**
     * 配置跨域访问（支持外网访问）
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 允许所有路径
                .allowedOriginPatterns("*")  // 允许所有来源
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 允许的请求方法
                .allowedHeaders("*")  // 允许的请求头
                .allowCredentials(true)  // 允许携带cookie
                .maxAge(3600);  // 预检请求的有效期
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截所有路径，但是除了/login
        registry.addInterceptor(loginCheckInterceptor).addPathPatterns("/**").excludePathPatterns("/login");
    }
}
