package com.ymmihw.libraries;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

public class DroolsBeanFactory {

  private KieServices kieServices = KieServices.Factory.get();

  private void getKieRepository() {
    final KieRepository kieRepository = kieServices.getRepository();
    kieRepository.addKieModule(
        new KieModule() {
          public ReleaseId getReleaseId() {
            return kieRepository.getDefaultReleaseId();
          }
        });
  }

  public KieSession getKieSession() {
    getKieRepository();
    KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

    kieFileSystem.write(ResourceFactory.newClassPathResource("BackwardChaining.drl"));

    KieBuilder kb = kieServices.newKieBuilder(kieFileSystem);
    kb.buildAll();
    KieModule kieModule = kb.getKieModule();

    KieContainer kContainer = kieServices.newKieContainer(kieModule.getReleaseId());

    return kContainer.newKieSession();
  }
}
