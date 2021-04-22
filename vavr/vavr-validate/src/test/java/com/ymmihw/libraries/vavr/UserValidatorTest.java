package com.ymmihw.libraries.vavr;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.Test;
import io.vavr.control.Validation.Invalid;
import io.vavr.control.Validation.Valid;

public class UserValidatorTest {

  @Test
  public void givenValidUserParams_whenValidated_thenInstanceOfValid() {
    UserValidator userValidator = new UserValidator();
    assertThat(userValidator.validateUser("John", "john@domain.com"), instanceOf(Valid.class));
  }

  @Test
  public void givenInvalidUserParams_whenValidated_thenInstanceOfInvalid() {
    UserValidator userValidator = new UserValidator();
    assertThat(userValidator.validateUser("John", "no-email"), instanceOf(Invalid.class));
  }
}
