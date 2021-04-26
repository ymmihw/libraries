package com.ymmihw.libraries.testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;

public class WebDriverContainerLiveTest {
  public BrowserWebDriverContainer<?> chrome =
      new BrowserWebDriverContainer<>().withCapabilities(DesiredCapabilities.chrome());

  @BeforeEach
  public void beforeEach() {
    chrome.start();
  }

  @Test
  public void whenNavigatedToPage_thenHeadingIsInThePage() {
    RemoteWebDriver driver = chrome.getWebDriver();
    driver.get("http://example.com");
    String heading = driver.findElement(By.xpath("/html/body/div/h1")).getText();
    assertEquals("Example Domain", heading);
  }

}
