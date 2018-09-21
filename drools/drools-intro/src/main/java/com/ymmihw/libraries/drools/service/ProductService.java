package com.ymmihw.libraries.drools.service;

import org.kie.api.runtime.KieSession;
import com.ymmihw.libraries.drools.config.DroolsBeanFactory;
import com.ymmihw.libraries.drools.model.Product;

public class ProductService {

  private KieSession kieSession = new DroolsBeanFactory().kieSession();

  public Product applyLabelToProduct(Product product) {
    kieSession.insert(product);
    kieSession.fireAllRules();
    System.out.println(product.getLabel());
    return product;

  }

}
