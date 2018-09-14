package com.ymmihw.libraries.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.ymmihw.petstore.client.api.PetApi;
import com.ymmihw.petstore.client.invoker.ApiClient;

@Configuration
public class PetStoreIntegrationConfig {

  @Bean
  public PetApi petpi() {
    return new PetApi(apiClient());
  }

  @Bean
  public ApiClient apiClient() {
    ApiClient apiClient = new ApiClient();
    return apiClient;
  }

}
