package com.ymmihw.libraries.jasypt;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;
import com.ymmihw.libraries.jasypt.simple.PropertyServiceForJasyptSimple;

@RunWith(SpringRunner.class)
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
