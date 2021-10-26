package com.ymmihw.libraries.app;

import com.ymmihw.libraries.model.Fare;
import com.ymmihw.libraries.model.TaxiRide;
import com.ymmihw.libraries.service.TaxiFareCalculatorService;
import com.ymmihw.libraries.service.TaxiFareConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationRunner {

  public static void main(String[] args) {
    ApplicationContext context =
        new AnnotationConfigApplicationContext(TaxiFareConfiguration.class);
    TaxiFareCalculatorService taxiFareCalculatorService =
        (TaxiFareCalculatorService) context.getBean(TaxiFareCalculatorService.class);
    TaxiRide taxiRide = new TaxiRide();
    taxiRide.setIsNightSurcharge(true);
    taxiRide.setDistanceInMile(190L);
    Fare rideFare = new Fare();
    taxiFareCalculatorService.calculateFare(taxiRide, rideFare);
  }
}
