package in.adwait.website.configurations;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentationConfiguration {

    @Bean
    public OpenAPI adwaitApiDoc() {
        return new OpenAPI()
                .info(new Info().title("adwait.in API")
                        .description("Api used by website adwait.in")
                        .version("1.0.0")
                        .license(new License().name("Apache 2.0").url("http://adwait.in")))
                .externalDocs(new ExternalDocumentation()
                        .description("Adwait Github Documentation")
                        .url("https://github.com/Adwai-T/Website-2.0-Spring"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .packagesToScan("in.adwait.website")
                .group("Public")
                .pathsToMatch("/**")
                .build();
    }
    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("Admin")
                .pathsToMatch("/admin/**")
                .build();
    }

/*  //---- Swagger Configurations ----
    @Bean
    public Docket websiteDocumentation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }  */
}
