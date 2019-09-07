//package com.people.rent.token;//package com.shaoman.token;
//
//
//import io.jsonwebtoken.Claims;
//import lombok.extern.slf4j.Slf4j;
//import net.sf.json.JSONObject;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.StringUtils;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
///**
// * Created by Msater Zg on 2017/4/5.
// * 拦截器
// */
//@Slf4j
//public class ApiInterceptor implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        Map<String, Object> rec = new LinkedHashMap<>();
//        String auth = request.getHeader("oAuth");
//        if (StringUtils.isEmpty(auth)) {
//            rec.put("code", 403);
//            rec.put("msg", "token没有认证通过!原因为：客户端请求参数中无token信息!");
//            rec.put("data", null);
//            rec.put("page", null);
//            JsonUtil.writeJson(rec, response);
//            return false;
//        }
//        log.info("稍曼系统传入的token:" + auth);
//        Claims c = JwtHelper.parseJWT(auth);
//        if (c == null) {
//            rec.put("code", 403);
//            rec.put("msg", "token没有认证通过!原因为：token信息错误!");
//            rec.put("data", null);
//            rec.put("page", null);
//            JsonUtil.writeJson(rec, response);
//            return false;
//        }
//        JSONObject json = JSONObject.fromObject(c);
//        long nbf = json.getLong("nbf");
//        long exp = json.getLong("exp");
//        String userId = json.getString("userId");
//        if (System.currentTimeMillis() > ((exp + nbf) * 500)) {
//            //Integer userId = json.getString("userId");
//            String accessToken = JwtHelper.createJWT(userId, 1000 * 60 * 60 * 24);
//            //logger.info("token:"+accessToken);
//            response.setHeader("oAuth", accessToken);
//      }
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//    }
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//
//    }
//
//}