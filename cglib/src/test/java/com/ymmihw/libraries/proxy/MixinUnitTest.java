package com.ymmihw.libraries.proxy;

import com.ymmihw.libraries.mixin.*;
import net.sf.cglib.proxy.Mixin;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MixinUnitTest {

  @Test
  public void givenTwoClasses_whenMixedIntoOne_thenMixinShouldHaveMethodsFromBothClasses()
      throws Exception {
    // when
    Mixin mixin =
        Mixin.create(
            new Class[] {Interface1.class, Interface2.class, MixinInterface.class},
            new Object[] {new Class1(), new Class2()});
    MixinInterface mixinDelegate = (MixinInterface) mixin;

    // then
    assertEquals("first behaviour", mixinDelegate.first());
    assertEquals("second behaviour", mixinDelegate.second());
  }
}
