package com.ymmihw.libraries.vavr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import io.vavr.collection.Seq;
import io.vavr.control.Option;

@SpringBootTest(classes = Application.class)
public class VavrRepositoryIntegrationTest {

  @Autowired
  private VavrUserRepository userRepository;

  @BeforeEach
  public void setup() {
    User user1 = new User();
    user1.setName("John");
    User user2 = new User();
    user2.setName("John");

    userRepository.save(user1);
    userRepository.save(user2);
  }

  @Test
  public void whenAddUsers_thenGetUsers() {
    Option<User> user = userRepository.findById(1L);
    assertFalse(user.isEmpty());
    assertTrue(user.get().getName().equals("John"));

    Seq<User> users = userRepository.findByName("John");
    assertEquals(2, users.size());
  }
}
