package com.conjunta.simbana.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuración personalizada para OpenAPI/Swagger
 */
@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema de Manejo de Efectivo en Ventanillas Bancarias")
                        .description("API REST para el manejo de efectivo en ventanillas bancarias. " +
                                   "Permite gestionar turnos de caja, registrar transacciones y " +
                                   "controlar denominaciones de billetes.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo de Desarrollo")
                                .email("desarrollo@conjunta.com")
                                .url("https://www.conjunta.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de desarrollo"),
                        new Server()
                                .url("https://api.simbana.com")
                                .description("Servidor de producción")
                ));
    }
} 