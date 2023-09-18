package com.wakeUpTogetUp.togetUp.config;

import com.wakeUpTogetUp.togetUp.common.Constant;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
class OpenApiConfig {
    @Bean
    public OpenAPI openApi() {
        Info info =new Info()
                .title(Constant.API_NAME)
                .version(Constant.API_VERSION)
                .description(Constant.API_DESCRIPTION);

        return new OpenAPI()
                .addServersItem(new Server().url("https://togetup.shop"))
                .addServersItem(new Server().url("http://localhost:9010"))
                .info(info)
                .addSecurityItem(new SecurityRequirement().addList(Constant.BEARER))
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        Constant.BEARER,
                                        new SecurityScheme()
                                                .name(Constant.BEARER)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme(Constant.JWT_PREFIX)
                                                .bearerFormat("JWT")

                                )

                );
    }
}