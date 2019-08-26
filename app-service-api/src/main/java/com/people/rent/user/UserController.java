package com.people.rent.user;


import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.rent.model.CommonResult;
import com.rent.model.bo.UserBO;
import com.rent.model.config.AliPayConfig;
import com.rent.model.dto.UserDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AliPayConfig alipayConfig;


    @ApiOperation(value = "统一登录接口", notes = "支付宝小程序唤起登录后调用", httpMethod = "POST")
    @PostMapping("/login/{authCode}")
    public CommonResult items(
            @ApiParam(name = "authCode",
                    value = "授权码",
                    required = true,
                    example = "授权码") @PathVariable String authCode) throws Exception {

        // 1. 服务端获取access_token、user_id
        AlipaySystemOauthTokenResponse response = getAccessToken(authCode);
        if (response.isSuccess()) {
            System.out.println("获取access_token - 调用成功");
            /**
             *  获取到用户信息后保存到数据
             *  1. 如果数据库不存在对用的 alipayUserId, 则注册
             *  2. 如果存在，则获取数据库中的信息再返回
             */
            String accessToken = response.getAccessToken();
            String alipayUserId = response.getUserId();
//            System.out.println("accessToken:" + accessToken);
//            System.out.println("alipayUserId:" + alipayUserId);

            // 2. 查询该用户是否存在
            UserBO userInfo = userService.queryUserIsExist(alipayUserId);
            if (userInfo != null) {
                // 如果用户存在，直接返回给前端，表示登录成功
                return CommonResult.success(userInfo);
            } else {
                // 如果用户不存在，则通过支付宝api获取用户的信息后，再注册用户到自己平台数据库
                // 获取会员信息
                AlipayUserInfoShareResponse aliUserInfo = getAliUserInfo(accessToken);
                if (aliUserInfo != null) {
                    UserDto newUser = new UserDto();
                    newUser.setAlipayUserId(alipayUserId);
                    newUser.setNickname(aliUserInfo.getNickName());

                    newUser.setIsCertified(aliUserInfo.getIsCertified().equals("T")?Boolean.TRUE : Boolean.FALSE);
                    newUser.setFaceImage(aliUserInfo.getAvatar());
                    userService.registUser(newUser);
                    return CommonResult.success(newUser);
                }
            }
        } else {
            System.out.println("获取access_token - 调用失败");
            return null;
        }
        return null;
    }

    // 服务端获取access_token、user_id
    private AlipaySystemOauthTokenResponse getAccessToken(String authCode) throws Exception {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                alipayConfig.getAliPayAppId(),                    // 1. 填入appid
                alipayConfig.getAliPayAppPrivateKey(),            // 2. 填入私钥
                "json",
                "GBK",
                alipayConfig.getAliPayAppPublicKey(),         // 3. 填入公钥
                "RSA2");
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setGrantType("authorization_code");
        request.setCode(authCode);        // 4. 填入前端传入的授权码authCode
        request.setRefreshToken("201208134b203fe6c11548bcabd8da5bb087a83b");    // 0. 不用管
        AlipaySystemOauthTokenResponse response = alipayClient.execute(request);

        return response;
    }

    // 获取支付宝用户信息
    private AlipayUserInfoShareResponse getAliUserInfo (String accessToken) throws Exception {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                alipayConfig.getAliPayAppId(),                    // 1. 填入appid
                alipayConfig.getAliPayAppPrivateKey(),            // 2. 填入私钥
                "json",
                "GBK",
                alipayConfig.getAliPayAppPublicKey(),         // 3. 填入公钥
                "RSA2");
        AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();
        AlipayUserInfoShareResponse response = alipayClient.execute(request, accessToken);
        if (response.isSuccess()) {
            System.out.println("获取会员信息 - 调用成功");
            return response;
        }

        return null;
    }
}
