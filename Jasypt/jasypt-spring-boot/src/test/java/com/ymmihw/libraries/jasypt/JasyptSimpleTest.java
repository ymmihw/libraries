package com.ymmihw.libraries.jasypt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import com.ymmihw.libraries.jasypt.simple.PropertyServiceForJasyptSimple;

@SpringBootTest
public class JasyptSimpleTest {

  @Autowired
  ApplicationContext appCtx;

  @Test
  public void whenDecryptedPasswordNeeded_GetFromService() {
    System.setProperty("jasypt.encryptor.password", "password");
    PropertyServiceForJasyptSimple service = appCtx.getBean(PropertyServiceForJasyptSimple.class);
    assertEquals("Password@2", service.getProperty());

    Environment environment = appCtx.getBean(Environment.class);
    assertEquals("Password@2", service.getPasswordUsingEnvironment(environment));
  }

}
