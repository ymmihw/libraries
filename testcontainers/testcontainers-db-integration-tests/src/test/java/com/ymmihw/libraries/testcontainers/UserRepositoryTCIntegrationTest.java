package com.ymmihw.libraries.testcontainers;

import com.ymmihw.libraries.testcontainers.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
@ActiveProfiles("tc")
@ContextConfiguration(initializers = {UserRepositoryTCIntegrationTest.Initializer.class})
public class UserRepositoryTCIntegrationTest extends UserRepositoryCommon {

  @Container
  public static PostgreSQLContainer<?> postgreSQLContainer =
      new PostgreSQLContainer<>("postgres:11.1").withDatabaseName("integration-tests-db")
          .withUsername("sa").withPassword("sa");

  @Test
  @Transactional
  public void givenUsersInDB_WhenUpdateStatusForNameModifyingQueryAnnotationNative_ThenModifyMatchingUsers() {
    userRepository.save(new User("SAMPLE", USER_EMAIL, ACTIVE_STATUS));
    userRepository.save(new User("SAMPLE1", USER_EMAIL2, ACTIVE_STATUS));
    userRepository.save(new User("SAMPLE", USER_EMAIL3, ACTIVE_STATUS));
    userRepository.save(new User("SAMPLE3", USER_EMAIL4, ACTIVE_STATUS));
    userRepository.flush();

    int updatedUsersSize =
        userRepository.updateUserSetStatusForNameNativePostgres(INACTIVE_STATUS, "SAMPLE");

    assertThat(updatedUsersSize).isEqualTo(2);
  }

  static class Initializer
      implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      TestPropertyValues
          .of("spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
              "spring.datasource.username=" + postgreSQLContainer.getUsername(),
              "spring.datasource.password=" + postgreSQLContainer.getPassword())
          .applyTo(configurableApplicationContext.getEnvironment());
    }
  }
}
