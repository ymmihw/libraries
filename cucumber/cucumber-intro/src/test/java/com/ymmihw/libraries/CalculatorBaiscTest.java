package com.ymmihw.libraries;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"classpath:features/calculator.feature"},
    glue = {"com.ymmihw.libraries"},
    plugin = {"pretty", "json:target/reports/json/calculator.json"})
public class CalculatorBaiscTest {
}
