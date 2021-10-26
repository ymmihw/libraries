package com.ymmihw.libraries;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieSession;

public class BackwardChainingIntegrationTest {
  private Result result;
  private KieSession ksession;

  @BeforeEach
  public void before() {
    result = new Result();
    ksession = new DroolsBeanFactory().getKieSession();
  }

  @Test
  public void whenWallOfChinaIsGiven_ThenItBelongsToPlanetEarth() {

    ksession.setGlobal("result", result);
    ksession.insert(new Fact("Asia", "Planet Earth"));
    ksession.insert(new Fact("China", "Asia"));
    ksession.insert(new Fact("Great Wall of China", "China"));

    ksession.fireAllRules();

    // Assert Decision one
    assertEquals(
        result.getValue(), "Decision one taken: Great Wall of China BELONGS TO Planet Earth");
  }
}
