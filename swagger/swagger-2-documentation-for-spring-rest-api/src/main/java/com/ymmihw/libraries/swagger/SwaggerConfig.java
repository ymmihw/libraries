package com.ymmihw.libraries.swagger;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import com.google.common.collect.Lists;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false)
        .globalResponseMessage(RequestMethod.GET,
            Lists.newArrayList(
                new ResponseMessageBuilder().code(500).message("500 message")
                    .responseModel(new ModelRef("Error")).build(),
                new ResponseMessageBuilder().code(403).message("Forbidden!").build()))
        .select().apis(RequestHandlerSelectors.basePackage("com.ymmihw.libraries.swagger"))
        .paths(PathSelectors.ant("/foos/*")).build().apiInfo(apiInfo());
  }


  private ApiInfo apiInfo() {
    return new ApiInfo("My REST API", "Some custom description of API.", "API TOS",
        "Terms of service", new Contact("John Doe", "www.example.com", "myeaddress@company.com"),
        "License of API", "API license URL", Collections.emptyList());
  }
}
