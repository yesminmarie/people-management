package io.github.yesminmarie.peoplemanagement.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("People Management")
                        .description("API to manage people")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Yesmin Lahoud")
                                .url("https://github.com/yesminmarie")
                                .email("ymslahoud@gmail.com"))
                        .termsOfService("TOC")
                        .license(new License().name("License").url("#"))
                );
    }
}