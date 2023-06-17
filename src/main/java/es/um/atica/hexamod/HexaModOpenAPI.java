package es.um.atica.hexamod;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@OpenAPIDefinition
@Configuration
public class HexaModOpenAPI {
    
    @Bean
    public OpenAPI baseOpenAPI() {
        return new OpenAPI()
            //.components(components)
            .info(new Info()
                .title("HexaMod OpenAPI Doc")
                .version("1.0.0")
                .description("This is the multimodule HexaMod open api documentation")
            );
    }

}
