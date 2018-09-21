package com.ymmihw.libraries.drools.service;

import java.io.IOException;
import org.kie.api.runtime.KieSession;
import com.ymmihw.libraries.drools.config.DroolsBeanFactory;
import com.ymmihw.libraries.drools.model.Applicant;
import com.ymmihw.libraries.drools.model.SuggestedRole;

public class ApplicantService {

  KieSession kieSession = new DroolsBeanFactory().kieSession();

  public SuggestedRole suggestARoleForApplicant(Applicant applicant, SuggestedRole suggestedRole)
      throws IOException {
    kieSession.insert(applicant);
    kieSession.setGlobal("suggestedRole", suggestedRole);
    kieSession.fireAllRules();
    System.out.println(suggestedRole.getRole());
    return suggestedRole;

  }
}
