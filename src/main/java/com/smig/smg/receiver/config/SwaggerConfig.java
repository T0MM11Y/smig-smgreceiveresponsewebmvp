package com.smig.smg.receiver.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Swagger/OpenAPI configuration
 * 
 * @author SMIG Development Team
 */
@Configuration
public class SwaggerConfig {

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(List.of(
                    new Server()
                        .url("http://localhost:8080" + contextPath)
                        .description("Development Server"),
                    new Server()
                        .url("https://your-domain.com" + contextPath)
                        .description("Production Server")
                ))
                .info(new Info()
                        .title("SMIG-SMG ReceiveResponseWEB MVP API")
                        .description("REST API untuk menerima dan menyimpan response message ke Oracle Database. " +
                                   "Aplikasi ini dibangun mengikuti SMIG Development Guide dengan fokus pada " +
                                   "core functionality POST dan GET API.")
                        .version("1.0.0-MVP")
                        .contact(new Contact()
                                .name("SMIG Development Team")
                                .email("support@smig.com"))
                        .license(new License()
                                .name("Proprietary")
                                .url("https://smig.com/license")));
    }
}
