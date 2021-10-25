package com.ymmihw.libraries;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ymmihw.libraries.Customer.CustomerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

public class DiscountExcelIntegrationTest {

  private KieSession kSession;

  @BeforeEach
  public void setup() {
    Resource resource = ResourceFactory.newClassPathResource("Discount.xls", getClass());
    kSession = new DroolsBeanFactory().getKieSession(resource);
  }

  @Test
  public void giveIndividualLongStanding_whenFireRule_thenCorrectDiscount() throws Exception {
    Customer customer = new Customer(CustomerType.INDIVIDUAL, 5);
    kSession.insert(customer);

    kSession.fireAllRules();

    assertEquals(customer.getDiscount(), 15);
  }

  @Test
  public void giveIndividualRecent_whenFireRule_thenCorrectDiscount() throws Exception {

    Customer customer = new Customer(CustomerType.INDIVIDUAL, 1);
    kSession.insert(customer);

    kSession.fireAllRules();

    assertEquals(customer.getDiscount(), 5);
  }

  @Test
  public void giveBusinessAny_whenFireRule_thenCorrectDiscount() throws Exception {
    Customer customer = new Customer(CustomerType.BUSINESS, 0);
    kSession.insert(customer);

    kSession.fireAllRules();

    assertEquals(customer.getDiscount(), 20);
  }
}
