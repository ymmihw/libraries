package com.ymmihw.libraries.liquibase;

import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ymmihw.libraries.liquibase.persist.Student;
import com.ymmihw.libraries.liquibase.persist.StudentRepository;
import lombok.AllArgsConstructor;

@SpringBootApplication
@RestController
@AllArgsConstructor
public class Application {
  private final StudentRepository studentRepository;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @GetMapping("/students")
  public List<Student> students() {
    return studentRepository.findAll();
  }
}
