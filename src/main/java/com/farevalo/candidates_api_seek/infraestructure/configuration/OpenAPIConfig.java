package com.farevalo.candidates_api_seek.infraestructure.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("basic")
                .name("basicAuth");

        Components components = new Components()
                .addSecuritySchemes("basicAuthe",securityScheme);

        SecurityRequirement securityRequirement = new SecurityRequirement().addList("basicAuthe");

        return new OpenAPI()
                .info(new Info()
                                .title("API de Candidatos")  // Título de la API
                                .description("Documentación de la API para la gestión de candidatos.")  // Descripción
                                .version("1.0")
                        // Versión
                )
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
