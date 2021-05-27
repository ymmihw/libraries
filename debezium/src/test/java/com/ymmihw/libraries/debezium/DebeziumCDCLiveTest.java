package com.ymmihw.libraries.debezium;

import com.ymmihw.libraries.debezium.repository.CustomerRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = DebeziumCDCApplication.class)
@ActiveProfiles("test")
public class DebeziumCDCLiveTest {

  @Autowired private CustomerRepository customerRepository;

  @Autowired
  @Qualifier("sourceJdbcTemplate")
  private NamedParameterJdbcTemplate jdbcTemplate;

  @BeforeEach
  public void clearData() {
    jdbcTemplate.update("delete from customer where id = :id", Collections.singletonMap("id", 1));
  }

  @DynamicPropertySource
  static void registerProperties(DynamicPropertyRegistry registry) {
    registry.add("customer.datasource.port", MySQLTestContainerConfiguration::getPort);
  }

  @Test
  public void whenInsertDataToSourceDatabase_thenCdcOk() throws InterruptedException {
    assertThat(customerRepository.findAll().size()).isZero();

    // insert data to source DB
    Map<String, Object> map = new HashMap<>();
    map.put("id", 1);
    map.put("fullname", "John Doe");
    map.put("email", "test@test.com");

    jdbcTemplate.update(
        "INSERT INTO customer(id, fullname, email) VALUES (:id, :fullname, :email)", map);

    // verify target DB
    Thread.sleep(10000);
    assertThat(customerRepository.findAll().size()).isNotZero();
  }
}
