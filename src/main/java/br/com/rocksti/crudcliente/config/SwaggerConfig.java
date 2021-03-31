package br.com.rocksti.crudcliente.config;

import io.swagger.models.auth.In;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
            .groupName("cliente")
            .select()
            .apis(RequestHandlerSelectors.basePackage("br.com.rocksti.crudcliente.web.rest"))
            .paths(PathSelectors.regex("/api/clientes.*"))
            .build()
            .apiInfo(apiInfo())
            .useDefaultResponseMessages(false)
            .securitySchemes(Collections.singletonList(new ApiKey(HttpHeaders.AUTHORIZATION, HttpHeaders.AUTHORIZATION, In.HEADER.name())));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Simples CRUD da API REST de Cliente")
            .description("Um exemplo de aplicação Spring Boot REST API")
            .version("0.0.1-SNAPSHOT")
            .license("Apache License Version 2.0")
            .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
            .contact(new Contact("Alberto", "https://albertomonteiro.dev", "alberto.costa.monteiro@gmail.com"))
            .build();
    }

    @Bean
    public Docket openAPISpringfoxManagementDocket() {
        return new Docket(DocumentationType.OAS_30).groupName("management").enable(false);
    }

    @Bean
    public Docket openAPISpringfoxApiDocket() {
        return new Docket(DocumentationType.OAS_30).enable(false);
    }
}
