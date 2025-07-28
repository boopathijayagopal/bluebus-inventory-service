package com.bluebus.inventoryservice.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI apiInfo() {

        return new OpenAPI()
                .info(
                        new Info()
                                .title("BlueBus Inventory Rest Api")
                                .description("Rest Api for managing inventory of BlueBus")
                                .version("1.0"));
    }
}
