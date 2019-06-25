package com.ymmihw.libraries.google.auto;

import java.util.Locale;
import com.google.auto.service.AutoService;

@AutoService(TranslationService.class)
public class GoogleTranslationServiceProvider implements TranslationService {
  @Override
  public String translate(String message, Locale from, Locale to) {
    // implementation details
    return message + " (translated by Google)";
  }
}
