package com.ymmihw.libraries.testcontainers;

import com.ymmihw.libraries.testcontainers.domain.User;
import com.ymmihw.libraries.testcontainers.util.BaeldungPostgresqlContainer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by adam.
 */
@Testcontainers
@SpringBootTest
@ActiveProfiles({"tc", "tc-auto"})
public class UserRepositoryTCAutoIntegrationTest extends UserRepositoryCommon {

  @Container
  public static PostgreSQLContainer<?> postgreSQLContainer =
      BaeldungPostgresqlContainer.getInstance();

  @Test
  @Transactional
  public void givenUsersInDB_WhenUpdateStatusForNameModifyingQueryAnnotationNativePostgres_ThenModifyMatchingUsers() {
    userRepository.save(new User("SAMPLE", USER_EMAIL, ACTIVE_STATUS));
    userRepository.save(new User("SAMPLE1", USER_EMAIL2, ACTIVE_STATUS));
    userRepository.save(new User("SAMPLE", USER_EMAIL3, ACTIVE_STATUS));
    userRepository.save(new User("SAMPLE3", USER_EMAIL4, ACTIVE_STATUS));
    userRepository.flush();

    int updatedUsersSize =
        userRepository.updateUserSetStatusForNameNativePostgres(INACTIVE_STATUS, "SAMPLE");

    assertThat(updatedUsersSize).isEqualTo(2);
  }
}
