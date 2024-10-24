package me.gt.paymenthub.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${server.url}")
    private String serverUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        Info info = new Info()
                .title("PaymentHub API")
                .version("1.0")
                .description("金流系統 API");

        return new OpenAPI()
                .info(info)
                .servers(List.of(new Server().url(serverUrl)));
    }
}
