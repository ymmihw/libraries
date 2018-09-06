package com.ymmihw.libraries.jasypt;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Main.class})
public class CustomJasyptTest {

  @Autowired
  ApplicationContext appCtx;

  @Test
  public void whenConfiguredExcryptorUsed_ReturnCustomEncryptor() {
    Environment environment = appCtx.getBean(Environment.class);
    assertEquals("Password@3", environment.getProperty("encryptedv3.property"));
  }
}
