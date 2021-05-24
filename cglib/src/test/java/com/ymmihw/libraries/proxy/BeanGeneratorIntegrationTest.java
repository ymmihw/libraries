package com.ymmihw.libraries.proxy;

import net.sf.cglib.beans.BeanGenerator;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BeanGeneratorIntegrationTest {

  @Test
  public void givenBeanCreator_whenAddProperty_thenClassShouldHaveFieldValue() throws Exception {
    // given
    BeanGenerator beanGenerator = new BeanGenerator();

    // when
    beanGenerator.addProperty("name", String.class);
    Object myBean = beanGenerator.create();
    Method setter = myBean.getClass().getMethod("setName", String.class);
    setter.invoke(myBean, "some string value set by a cglib");

    // then
    Method getter = myBean.getClass().getMethod("getName");
    assertEquals("some string value set by a cglib", getter.invoke(myBean));
  }
}
