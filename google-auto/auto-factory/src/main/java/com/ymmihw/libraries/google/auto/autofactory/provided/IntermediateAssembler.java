package com.ymmihw.libraries.google.auto.autofactory.provided;

import javax.inject.Provider;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import com.ymmihw.libraries.google.auto.autofactory.model.Camera;

/**
 * @author aiet
 */
@AutoFactory
public class IntermediateAssembler {

  private final Provider<Camera> camera;
  private final String otherParts;

  public IntermediateAssembler(@Provided Provider<Camera> camera, String otherParts) {
    this.camera = camera;
    this.otherParts = otherParts;
  }
}
