package com.ymmihw.libraries;

import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java8.LambdaGlue;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HookConfig implements LambdaGlue {
  public HookConfig() {
    Before(1, () -> startBrowser());
  }

  @Before(order = 2, value = "@Screenshots")
  public void beforeScenario(Scenario scenario) {
    takeScreenshot();
  }

  @After
  public void afterScenario(Scenario scenario) {
    takeScreenshot();
  }

  @BeforeStep
  public void beforeStep(Scenario scenario) {
    takeScreenshot();
  }

  @AfterStep
  public void afterStep(Scenario scenario) {
    takeScreenshot();
    closeBrowser();
  }

  public void takeScreenshot() {
    log.debug("takeScreenshot");
    // code to take and save screenshot
  }

  public void startBrowser() {
    log.debug("startBrowser");
    // code to open browser
  }

  public void closeBrowser() {
    log.debug("closeBrowser");
    // code to close browser
  }

}
