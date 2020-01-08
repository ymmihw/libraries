package com.ymmihw.libraries;

import org.junit.runner.RunWith;
import io.cucumber.java8.En;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features/book-store-with-hooks.feature",
    glue = "com.ymmihw.libraries")
public class BookStoreWithHooksIntegrationTest implements En {
 
}
