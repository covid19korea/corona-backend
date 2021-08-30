package com.corona.backend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.oas.annotations.EnableOpenApi
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@Configuration
@EnableOpenApi
class SwaggerConfig {

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.OAS_30)
            .useDefaultResponseMessages(false)
            .groupName("V1")
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.corona.backend.controller"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(metaData())
    }

    private fun metaData(): ApiInfo {
        return ApiInfoBuilder()
            .title("Corona backend-server REST API")
            .description("Corona API V1")
            .version("V1")
            .build()
    }
}
