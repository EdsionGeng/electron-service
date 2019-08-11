package com.people.rent.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author EdsionGeng
 * @create 2017/8/26
 * @DESCRIPTION自动定义接口文档
 */
@EnableSwagger2
@Configuration
public class Swagger2Config {
    @Bean
    public Docket createRestApi() {
         ParameterBuilder tokenPar = new ParameterBuilder();
         List<Parameter> pars = new ArrayList<>();
         tokenPar.name("oAuth").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
            pars.add(tokenPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.shaoman.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
    }

    //构建 api文档的详细信息函数
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("稍曼API文档")
                //创建人
                .contact(new Contact("研发组", "www.baidu.com", "18717871283@163.com"))
                //版本号
                .version("1.0")
                //描述
                .description("前后端并行开发，合作愉快")
                .build();
    }
}
