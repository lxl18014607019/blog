package com.personal.blog.config;

import com.personal.blog.interceptor.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: luxinlong
 * @Description:
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截的请求
        String[] byIntercepting = {"/admin/*","/admin/*/*","/projects/*"};

        //不拦截的请求
        String[] notByIntercepting = {"/admin","/admin/login","/projects/code"};

        registry.addInterceptor(new MyInterceptor()).addPathPatterns(byIntercepting).excludePathPatterns(notByIntercepting);
    }



    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 所有的当前站点的请求地址，都支持跨域访问。
//                .allowedOrigins("*")
                .allowedOriginPatterns("*")// 所有的外部域都可跨域访问。 如果是localhost则很难配置，因为在跨域请求的时候，外部域的解析可能是localhost、127.0.0.1、主机名
                .allowCredentials(true) // 是否支持跨域用户凭证
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 当前站点支持的跨域请求类型是什么
                .maxAge(3600); // 超时时长设置为1小时。 时间单位是秒。
    }
}

