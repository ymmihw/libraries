package com.ymmihw.libraries;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"classpath:features/calculator.feature"},
    glue = {"com.ymmihw.libraries"})
public class CalculatorBaiscTest {
}
