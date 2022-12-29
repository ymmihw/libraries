package com.ymmihw.libraries;

import com.blazebit.persistence.integration.view.spring.EnableEntityViews;
import com.blazebit.persistence.spring.data.repository.config.EnableBlazeRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@ComponentScan("com.ymmihw.libraries")
@EnableEntityViews(basePackages = {"com.ymmihw.libraries.view"})
@EnableBlazeRepositories(basePackages = "com.ymmihw.libraries.repository")
public class TestContextConfig {
  @Autowired private SqlUtils sqlUtils;

  @PostConstruct
  public void postConstruct() {
    sqlUtils.execute("INSERT INTO Person(id, name, age) VALUES(1, 'Peter', 18)");
    sqlUtils.execute("INSERT INTO Person(id, name, age) VALUES(2, 'Emily', 36)");
    sqlUtils.execute("INSERT INTO Person(id, name, age) VALUES(3, 'John', 27)");

    sqlUtils.execute(
        "INSERT INTO Post(id, title, content, author_id) VALUES(1, 'Blaze Persistence', 'Blaze Content', 1)");
    sqlUtils.execute(
        "INSERT INTO Post(id, title, content, author_id) VALUES(2, 'Jacoco', 'Jacoco Content', 1)");
    sqlUtils.execute(
        "INSERT INTO Post(id, title, content, author_id) VALUES(3, 'Spring', 'Spring Content', 2)");
    sqlUtils.execute(
        "INSERT INTO Post(id, title, content, author_id) VALUES(4, 'Spring Boot', 'Spring Boot Content', 3)");
    sqlUtils.execute(
        "INSERT INTO Post(id, title, content, author_id) VALUES(5, 'Java 17', 'Java Content', 3)");
    sqlUtils.execute(
        "INSERT INTO Post(id, title, content, author_id) VALUES(6, 'Functional Programming', 'Functional Programming Content', 3)");
    sqlUtils.execute(
        "INSERT INTO Post(id, title, content, author_id) VALUES(7, 'Unit Testing', 'Unit Testing Content', 3)");
  }
}
