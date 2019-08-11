package com.rent.util.config;//package com.rent.util.config;
//
//
//import com.shaoman.filter.AesFilter;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.servlet.DispatcherType;
//import javax.servlet.Filter;
//import java.util.ArrayList;
//import java.util.List;
//
//@Configuration
//public class WebMvcConfig {
//
//
//    @Bean
//    FilterRegistrationBean aesFilterRegistration() {
//        FilterRegistrationBean filterReg = new FilterRegistrationBean(new AesFilter());
//        //filterReg.setFilter(new DelegatingFilterProxy("aesFilter"));
//        //优先级
//        filterReg.setOrder(70);
//        filterReg.setDispatcherTypes(DispatcherType.REQUEST);
//        //匹配路径
//        List<String> urlPatterns = new ArrayList<>();
//        urlPatterns.add("/*");
//        filterReg.addUrlPatterns("/*");
//        filterReg.addInitParameter("exclusions", "/swagger-resources/configuration/security,/swagger-resources/configuration/ui," +
//                "/swagger-resources,/swagger-ui.html,/favicon.ico,/v2/api-docs,/doc.html,/mobapi/user/check,/mobapi/category/list,/mobapi/user/login,/ticket/ticket,/ticket/query,/mobapi/share/detail,/mobapi/share/cash," +
//                "/mobapi/user/timeout,/mobapi/user/register,/mobapi/ticket,/mobapi/gift,/mobapi/user/updatePassword,/mobapi/recharge," +
//                "/images/**.png,/ticket_res/**/**.js,/yaoqingyouli_res/**/**.js,/mobapi/user/index,/images/**.jpg,/build/css/**.css,/css/**.css," +
//                "/ticket_res/**/**.png,/ticket_res/**/**.css,/ticket_res/**/**.js,/yaoqingyouli_res/**/**.css,/yaoqingyouli_res/**/**.png,/js/**.js,/www.shareinstall.com.cn/js/page/jshareinstall.min.js,/mobapi/invite,/mobapi/share," +
//                "/mobapi/app/checkUpdate,/mobapi/verify/send,/mobapi/verify/check," +
//                "/mobapi/category/list,/mobapi/app/version,/mobapi/vip/iosShowable,/notify/alipayOrder,/notify/alipayVip,/mobapi/user/checkPhoneNum");
//        filterReg.setUrlPatterns(urlPatterns);
//        return filterReg;
//    }
//
//    @Bean
//    public Filter aesFilter() {
//        return new AesFilter();
//    }
//
//
//}
