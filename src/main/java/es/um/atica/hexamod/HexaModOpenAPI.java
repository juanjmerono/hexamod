package es.um.atica.hexamod;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;

@OpenAPIDefinition
@Configuration
public class HexaModOpenAPI {
    
    private ApiResponse buildApiResponse(String desc, String... errorIDs) {
        MediaType mediaType = new MediaType();
        for (String id: errorIDs) {
            mediaType.addExamples(id,new Example().value(null));
        }
        return new ApiResponse().content(
            new Content().addMediaType(
                org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
                mediaType)
            ).description(desc);
    }

    @Bean
    public OpenAPI baseOpenAPI() {

        Components components = new Components();
        components.addResponses("unauthorized", buildApiResponse("Unauthorized!","401"));
        components.addResponses("ok", buildApiResponse("Success!","200"));

        return new OpenAPI()
            .components(components)
            .info(new Info()
                .title("HexaMod OpenAPI Doc")
                .version("1.0.0")
                .description("This is the multimodule HexaMod open api documentation")
            );
    }

}
