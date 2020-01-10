package com.szwujie.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

// http://localhost:4007/sso/swagger-ui.html
// http://localhost:4007/ss0/v2/api-docs

@Configuration
@EnableSwagger2
public class Swagger2 {

	@Bean
	public Docket createRestApi() {
//        ParameterBuilder tokenPar = new ParameterBuilder();
//        List<Parameter> pars = new ArrayList<>();
//        tokenPar.name("x-auth-token").
//                description("登录后返回的token串（仅登录、注册非必填）")
//                .modelRef(new ModelRef("string")).parameterType("header")
//                .required(false).build(); //header中的token参数非必填
//        pars.add(tokenPar.build());
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.szwujie.websocket.controller"))
                .build();
                //.securitySchemes(securitySchemes())
               // .securityContexts(securityContexts())
               // .globalOperationParameters(pars);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("WebSocket API")
				.description("WebSocket API document")
				.termsOfServiceUrl("http://test..")
				.version("1.0").build();
	}

    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeyList = new ArrayList();
        apiKeyList.add(new ApiKey("x-auth-token", "x-auth-token", "header"));
        return apiKeyList;
    }

    /**
     * 设置安全验证内容
     * <p>设置需要使用参数的接口（或者说，是去除掉不需要使用参数的接口），
     * 如PathSelectors.regex(“^(?!auth).*$”)，所有包含”auth”的接口不需要使用securitySchemes。
     * 即不需要使用上文中设置的名为“x-auth-token”，type为“header”的参数。</p>
     */
    private List<SecurityContext> securityContexts() {
        // 所有的接口均需要安全验证
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(SecurityContext.builder().securityReferences(defaultAuth()).
                forPaths(PathSelectors.regex("[\\s\\S]")).build());
        return securityContexts;
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }

    @Bean
    UiConfiguration uiConfig() {
        return new UiConfiguration(null, "none", "alpha", "schema",
                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS, false, true, 60000L);
    }

}