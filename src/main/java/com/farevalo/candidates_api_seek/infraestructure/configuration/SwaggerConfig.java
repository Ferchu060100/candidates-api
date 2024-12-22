package com.farevalo.candidates_api_seek.infraestructure.configuration;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
/*import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;*/

/*@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

*//*    private ApiInfo apiDetails() {
        return new ApiInfoBuilder()
                .title("API Documentación con Swagger 2")
                .description("Documentación automática generada por Swagger")
                .version("1.0")
                .build();
    }*//*
}*/


/*@Configuration
*//*@SecuritySchemes({
        @SecurityScheme(name = "basicAuth", type = SecuritySchemeType.HTTP, scheme = "basic")
})*//*
public class SwaggerConfig {

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

                //.addSecurityItem(new SecurityRequirement().addList("basicAuth"));
    }
}*/
