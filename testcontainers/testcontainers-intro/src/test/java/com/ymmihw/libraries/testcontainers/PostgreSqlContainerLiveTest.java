package com.ymmihw.libraries.testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class PostgreSqlContainerLiveTest {
  public PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>(
      DockerImageName.parse("postgres").withTag(PostgreSQLContainer.DEFAULT_TAG));

  @BeforeEach
  public void beforeEach() {
    postgresContainer.start();
  }

  @Test
  public void whenSelectQueryExecuted_thenResulstsReturned() throws Exception {
    ResultSet resultSet = performQuery(postgresContainer, "SELECT 1");
    resultSet.next();
    int result = resultSet.getInt(1);
    assertEquals(1, result);
  }

  private ResultSet performQuery(PostgreSQLContainer<?> postgres, String query)
      throws SQLException {
    String jdbcUrl = postgres.getJdbcUrl();
    String username = postgres.getUsername();
    String password = postgres.getPassword();
    Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
    return conn.createStatement().executeQuery(query);
  }
}
