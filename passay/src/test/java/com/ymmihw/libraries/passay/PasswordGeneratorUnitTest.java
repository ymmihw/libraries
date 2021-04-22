package com.ymmihw.libraries.passay;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

public class PasswordGeneratorUnitTest {

  @Test
  public void givenDigitsGenerator_whenGeneratingPassword_thenPasswordContainsDigitsHasLength10() {
    CharacterRule digits = new CharacterRule(EnglishCharacterData.Digit);

    PasswordGenerator passwordGenerator = new PasswordGenerator();
    String password = passwordGenerator.generatePassword(10, digits);

    assertTrue(password.length() == 10);
    assertTrue(containsOnlyCharactersFromSet(password, "0123456789"));
  }

  @Test
  public void givenCustomizedRule_whenGenerating_thenGeneratedPasswordContainsCustomizedCharacters() {
    CharacterRule specialCharacterRule = new CharacterRule(new CharacterData() {
      @Override
      public String getErrorCode() {
        return "SAMPLE_ERROR_CODE";
      }

      @Override
      public String getCharacters() {
        return "ABCxyz123!@#";
      }
    });

    PasswordGenerator passwordGenerator = new PasswordGenerator();
    String password = passwordGenerator.generatePassword(10, specialCharacterRule);

    assertTrue(containsOnlyCharactersFromSet(password, "ABCxyz123!@#"));
  }

  private boolean containsOnlyCharactersFromSet(String password, String setOfCharacters) {
    return Stream.of(password.split("")).allMatch(it -> setOfCharacters.contains(it));
  }

}
