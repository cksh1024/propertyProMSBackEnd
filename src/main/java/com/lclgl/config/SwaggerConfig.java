package com.lclgl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @author cksh
 * @create 2020-12-12 22:37
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket docket(@PathVariable("apiInfo") ApiInfo apiInfo) {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .groupName("lclgl")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lclgl.controller"))
                .build();
    }

    @Bean
    public ApiInfo apiInfo() {
        Contact contact = new Contact("lclgl", "http://localhost:8080", "lclgl@qq.com");
        return new ApiInfo(
                "lclgl",
                "lclgl",
                "1.0",
                "http://localhost:8080/getStuList",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>());
    }
}
