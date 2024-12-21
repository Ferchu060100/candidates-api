package com.farevalo.candidates_api_seek.infraestructure.configuration;


import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@SecuritySchemes({
        @SecurityScheme(name = "basicAuth", type = SecuritySchemeType.HTTP, scheme = "basic")
})
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Candidatos")  // Título de la API
                        .description("Documentación de la API para la gestión de candidatos.")  // Descripción
                        .version("1.0")
                        // Versión
                )
                .addSecurityItem(new SecurityRequirement().addList("basicAuth"));
    }
}