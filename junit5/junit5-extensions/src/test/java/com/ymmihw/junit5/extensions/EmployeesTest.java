package com.ymmihw.junit5.extensions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({EnvironmentExtension.class, EmployeeDatabaseSetupExtension.class,
    EmployeeDaoParameterResolver.class})
@ExtendWith(LoggingExtension.class)
@ExtendWith(IgnoreFileNotFoundExceptionExtension.class)
public class EmployeesTest {

  private EmployeeJdbcDao employeeDao;

  public EmployeesTest(EmployeeJdbcDao employeeDao) {
    this.employeeDao = employeeDao;
  }

  @Test
  public void whenAddEmployee_thenGetEmployee() throws SQLException {
    Employee emp = new Employee(1, "john");
    employeeDao.add(emp);
    assertEquals(1, employeeDao.findAll().size());
  }

  @Test
  public void whenGetEmployees_thenEmptyList() throws SQLException {
    assertEquals(0, employeeDao.findAll().size());
  }

}
