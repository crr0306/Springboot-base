package com.spring.base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private String title = "国联小店";

    private String description = "自研";

    private String version = "1.0.1";

    //扫描的路径包,设置basePackage会将包下的所有被@Api标记类的所有方法作为api
    private String basePackage = "com.gl.weidian.controller";

    //控制swagger是否启动开发模式 测试模式 生产模式
    @Value("${swagger2.enable}")
    private boolean enable;

    //指定是否需要给所有接口添加头部信息
    private boolean header = false;

    @Bean
    public Docket createRestApi() {
        List<Parameter> pars = header == true ? addParameters() : null;
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars)
                .enable(enable);
    }

    /**
     * 为swagger接口的时候添加头部信息
     * 如何有多个头部信息 可以add()直接添加到pars就可以了
     *
     * @return
     */
    private List<Parameter> addParameters() {
        List<Parameter> pars = new ArrayList<Parameter>();
        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name("token").description("(模拟token传入)非必填 header").modelRef(new ModelRef("string")).parameterType("header").required(false);

        //添加一个token 用于认证用户本读是否存在token
        pars.add(tokenPar.build());
        return pars;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                //访问地址：http://localhost:8060/glwd/swagger-ui.html
                .termsOfServiceUrl("localhost:8060/glwd/swagger/")
                .version(version)
                .build();
    }
}