package com.ymmihw.libraries.testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import com.ymmihw.libraries.testcontainers.domain.User;
import com.ymmihw.libraries.testcontainers.util.BaeldungPostgresqlContainer;

/**
 * Created by adam.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"tc", "tc-auto"})
public class UserRepositoryTCAutoIntegrationTest extends UserRepositoryCommon {

  @ClassRule
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
  @Override
  @AfterEach
  public void cleanUp() {
    userRepository.deleteAll();
  }
}
