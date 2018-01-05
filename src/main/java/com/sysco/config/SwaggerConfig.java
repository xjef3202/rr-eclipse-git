package com.sysco.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/health|/nutritions.*{0,}|/products.*{0,}|/users.*{0,}|/itemHistory.*{0,}|/pdfReports.*{0,}|/customers.*{0,}|/carts.*{0,}|/orders.*{0,}|/item.*{0,}"))
                .build().apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Orchestration Service REST API",
                "",
                "1.0",
                "",
                "syssmxplus@gmail.com",
                "License of API",
                "API license URL");
        return apiInfo;
    }
}
