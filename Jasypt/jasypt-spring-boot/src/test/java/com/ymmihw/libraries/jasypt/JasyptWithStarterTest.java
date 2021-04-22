package com.ymmihw.libraries.jasypt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import com.ymmihw.libraries.jasypt.starter.PropertyServiceForJasyptStarter;

@SpringBootTest
public class JasyptWithStarterTest {

  @Autowired
  ApplicationContext appCtx;

  @Test
  public void whenDecryptedPasswordNeeded_GetFromService() {
    System.setProperty("jasypt.encryptor.password", "password");
    PropertyServiceForJasyptStarter service = appCtx.getBean(PropertyServiceForJasyptStarter.class);
    assertEquals("Password@1", service.getProperty());
    Environment environment = appCtx.getBean(Environment.class);
    assertEquals("Password@1", service.getPasswordUsingEnvironment(environment));
  }
}
