package com.ymmihw.libraries.drools.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

public class DroolsBeanFactory {
  private static final String RULES_PATH = "com/ymmihw/libraries/drools/rules/";

  private KieServices kieServices() {
    return KieServices.Factory.get();
  }

  private List<String> ruleFiles() {
    return Arrays.asList("SuggestApplicant.drl", "Product_rules.xls");
  }

  private KieFileSystem kieFileSystem() throws IOException {
    KieFileSystem kieFileSystem = kieServices().newKieFileSystem();
    for (String file : ruleFiles()) {
      kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_PATH + file, "UTF-8"));
    }
    return kieFileSystem;

  }

  private KieContainer kieContainer() throws IOException {
    KieBuilder kb = kieServices().newKieBuilder(kieFileSystem());
    kb.buildAll();
    KieModule kieModule = kb.getKieModule();

    return kieServices().newKieContainer(kieModule.getReleaseId());
  }

  public KieSession kieSession() {
    try {
      return kieContainer().newKieSession();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
