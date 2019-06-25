package com.ymmihw.libraries.google.auto.autofactory.provider;

import javax.inject.Provider;
import com.ymmihw.libraries.google.auto.autofactory.model.Camera;

/**
 * @author aiet
 */
public class SonyCameraProvider implements Provider<Camera> {

  private static int sonyCameraSerial = 1;

  @Override
  public Camera get() {
    return new Camera("Sony", String.format("%03d", sonyCameraSerial++));
  }

}
