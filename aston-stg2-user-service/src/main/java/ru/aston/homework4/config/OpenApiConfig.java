package ru.aston.homework4.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Service API")
                        .version("1.0")
                        .description("Документация REST API для управления пользователями")
                        .contact(new Contact()
                                .name("Команда разработки")
                                .email("dev@example.com")
                        )
                );
    }
}
