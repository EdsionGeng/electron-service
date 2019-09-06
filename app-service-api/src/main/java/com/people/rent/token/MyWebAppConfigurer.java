package com.people.rent.token;///***************************************************


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Master Zg on 2017/4/5.
 * 拦截器管理工具
 */
@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
      registry.addInterceptor(new ApiInterceptor()).addPathPatterns("/**").excludePathPatterns("/mobapi/user/timeout", "/swagger-ui.html", "doc.html", "/swagger-resources",  "/mobapi/user/delete",
               "/swagger-resources/configuration/security", "/swagger-resources/configuration/ui");
        super.addInterceptors(registry);
    }

 }