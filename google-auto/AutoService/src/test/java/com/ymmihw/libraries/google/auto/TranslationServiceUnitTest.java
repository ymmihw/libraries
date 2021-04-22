package com.ymmihw.libraries.google.auto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TranslationServiceUnitTest {

  private ServiceLoader<TranslationService> loader;

  @BeforeEach
  public void setUp() {
    loader = ServiceLoader.load(TranslationService.class);
  }

  @Test
  public void whenServiceLoaderLoads_thenLoadsAllProviders() {
    long count = StreamSupport.stream(loader.spliterator(), false).count();
    assertEquals(2, count);
  }

  @Test
  public void whenServiceLoaderLoadsGoogleService_thenGoogleIsLoaded() {
    TranslationService googleService = StreamSupport.stream(loader.spliterator(), false)
        .filter(p -> p.getClass().getSimpleName().equals("GoogleTranslationServiceProvider"))
        .findFirst().get();

    String message = "message";
    assertEquals(message + " (translated by Google)", googleService.translate(message, null, null));
  }
}
