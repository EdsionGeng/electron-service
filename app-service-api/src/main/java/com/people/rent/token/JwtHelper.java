//package com.people.rent.token;////package com.wsd.knowledge.util;
////import io.jsonwebtoken.Claims;
////import io.jsonwebtoken.JwtBuilder;
////import io.jsonwebtoken.Jwts;
////import io.jsonwebtoken.SignatureAlgorithm;
////import net.sf.json.JSONObject;
////import org.slf4j.Logger;
////import org.slf4j.LoggerFactory;
////
////import javax.crypto.spec.SecretKeySpec;
////import javax.xml.bind.DatatypeConverter;
////import java.io.IOException;
////import java.security.Key;
////import java.util.Date;
////public class JwtHelper {
////    private static final Logger logger = LoggerFactory.getLogger(JwtHelper.class);
////
////    /**
////     * 该方法 在验证jsonWebToken有效性之后调用
////     * @param jsonWebToken
////     * @return
////     */
////    public static Integer getUserId(String jsonWebToken){
////        Claims c = parseJWT(jsonWebToken);
////        JSONObject json = JSONObject.fromObject(c);
////        return json.getInt("userId");
////    }
////
////
////    public static Claims parseJWT(String jsonWebToken){
////        String base64Security = null;
////        try {
////            base64Security = ReadPropertiesUtil.readPropertiesValueByKey("application.properties", "base64Security");
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////        try{
////            Claims claims = Jwts.parser()
////                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
////                    .parseClaimsJws(jsonWebToken).getBody();
////            return claims;
////        }catch(Exception ex){
////            return null;
////        }
////    }
////    public static Claims parseJWT(String jsonWebToken, String base64Security){
////        try{
////            Claims claims = Jwts.parser()
////                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
////                    .parseClaimsJws(jsonWebToken).getBody();
////            return claims;
////        }catch(Exception ex)
////        {
////            return null;
////        }
////    }
////    /**
////     *
////     * @param userId
////     * @param TTLMillis
////    * @return
////     */
////    public static String createJWT(Integer userId,long TTLMillis){
////        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
////        long nowMillis = System.currentTimeMillis();
////        Date now = new Date(nowMillis);
////        //生成签名密钥
////        String base64Security = null;
////        try {
////            base64Security = ReadPropertiesUtil.readPropertiesValueByKey("application.properties", "base64Security");
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
////        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
////
////        //添加构成JWT的参数
////        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
////               // .claim("role", role)
////             //   .claim("userInfo", user)
////                .claim("userId",userId)
////            //    .setIssuer(issuer)
////            //    .setAudience(audience)
////                .signWith(signatureAlgorithm, signingKey);
////        //添加Token过期时间
////        if (TTLMillis >= 0) {
////            long expMillis = nowMillis + TTLMillis;
////            Date exp = new Date(expMillis);
////            builder.setExpiration(exp).setNotBefore(now);
////        }
////        //生成JWT
////        return builder.compact();
////    }
////
////
////
////    /**
////     *
////      * @param userId
////     * @param TTLMillis
////     * @param base64Security
////     * @return
////     */
////    public static String createJWT(Integer userId,long TTLMillis, String base64Security){
////        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
////        long nowMillis = System.currentTimeMillis();
////        Date now = new Date(nowMillis);
////        //生成签名密钥
////        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
////        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
////
////        //添加构成JWT的参数
////        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
////                // .claim("role", role)
////               // .claim("userInfo", user)
////                .claim("userId",userId)
////                //    .setIssuer(issuer)
////                //    .setAudience(audience)
////                .signWith(signatureAlgorithm, signingKey);
////        //添加Token过期时间
////        if (TTLMillis >= 0) {
////            long expMillis = nowMillis + TTLMillis;
////            Date exp = new Date(expMillis);
////            builder.setExpiration(exp).setNotBefore(now);
////        }
////        //生成JWT
////        return builder.compact();
////    }
////
////
////
////
////
////
////    public static void main(String[] args) {
////          logger.info(createJWT("1",1000*60*5));
////            Claims c =  parseJWT("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIxIiwiZXhwIjoxNTE3NjM1NTUzLCJuYmYiOjE1MTc2MjgzNTN9.VNgzqZh0HPB1Ifc_lP7P5hOngM66wzO3K_n5Qpb_7O4");
////            logger.info(c.toString());
////            System.out.println("<><><><><>"+c.get("userId"));
////
////    }
////}
//
//import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.JwtBuilder;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import lombok.extern.slf4j.Slf4j;
//import net.sf.json.JSONObject;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.spec.SecretKeySpec;
//import javax.xml.bind.DatatypeConverter;
//import java.security.Key;
//import java.util.Date;
//
//@Component
//@Slf4j
//public class JwtHelper {
//
//    private static String base64Security;
//
//    @Value("${base64Security}")
//    public void JwtHelper(String base64Security) {
//        this.base64Security = base64Security;
//    }
//
//    /**
//     * 该方法 在验证jsonWebToken有效性之后调用
//     *
//     * @param jsonWebToken
//     * @return
//     */
//    public static String getUserId(String jsonWebToken) {
//        Claims c = parseJWT(jsonWebToken, base64Security);
//
//        JSONObject json = JSONObject.fromObject(c);
//        log.info("解析获取到的Json:::{}", json);
//        if (json.isNullObject()) {
//            return null;
//        }
//        return json.getString("userId");
//    }
//
//
//    public static Claims parseJWT(String jsonWebToken) {
//        //logger.info("--------------"+base64Security+"-----------");
//        try {
//            Claims claims = Jwts.parser()
//                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
//                    .parseClaimsJws(jsonWebToken).getBody();
//            return claims;
//        } catch (Exception ex) {
//            return null;
//        }
//    }
//
//
//    public static Claims parseJWT(String jsonWebToken, String base64Security) {
//        try {
//            Claims claims = Jwts.parser()
//                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
//                    .parseClaimsJws(jsonWebToken).getBody();
//            return claims;
//        } catch (Exception ex) {
//            return null;
//        }
//    }
//
//
//
//    /**
//     *
//     * @param userId
//     * @param TTLMillis
//     * @param base64Security
//     * @return
//     */
//    public static String createJWT(Integer userId,long TTLMillis, String base64Security){
//        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
//        long nowMillis = System.currentTimeMillis();
//        Date now = new Date(nowMillis);
//        //生成签名密钥
//        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
//        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
//
//        //添加构成JWT的参数
//        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
//                // .claim("role", role)
//                // .claim("userInfo", user)
//                .claim("userId",userId)
//                //    .setIssuer(issuer)
//                //    .setAudience(audience)
//                .signWith(signatureAlgorithm, signingKey);
//        //添加Token过期时间
//        if (TTLMillis >= 0) {
//            long expMillis = nowMillis + TTLMillis;
//            Date exp = new Date(expMillis);
//            builder.setExpiration(exp).setNotBefore(now);
//        }
//        //生成JWT
//        return builder.compact();
//    }
//
//
//    public static void main(String[] args) {
//        Claims c = parseJWT("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIxNDQiLCJleHAiOjE1MjY2NDU1OTgsIm5iZiI6MTUyNjYzODM5OH0.5wZNFRalvSNU2mx_hkWyo2yTUePToKQBKim5u61Z2LQ");
//        log.info(c.toString());
//        System.out.println("<><><><><>" + c.get("userId"));
//    }
//}
