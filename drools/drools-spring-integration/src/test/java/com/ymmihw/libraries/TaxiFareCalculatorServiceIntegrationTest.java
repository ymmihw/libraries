package com.ymmihw.libraries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ymmihw.libraries.model.Fare;
import com.ymmihw.libraries.model.TaxiRide;
import com.ymmihw.libraries.service.TaxiFareCalculatorService;
import com.ymmihw.libraries.service.TaxiFareConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = TaxiFareConfiguration.class)
public class TaxiFareCalculatorServiceIntegrationTest {

  @Autowired private TaxiFareCalculatorService taxiFareCalculatorService;

  @Test
  public void whenNightSurchargeFalseAndDistanceLessThan10_thenFixFareWithoutNightSurcharge() {
    TaxiRide taxiRide = new TaxiRide();
    taxiRide.setIsNightSurcharge(false);
    taxiRide.setDistanceInMile(9L);
    Fare rideFare = new Fare();
    Long totalCharge = taxiFareCalculatorService.calculateFare(taxiRide, rideFare);

    assertNotNull(totalCharge);
    assertEquals(Long.valueOf(70), totalCharge);
  }

  @Test
  public void whenNightSurchargeTrueAndDistanceLessThan10_thenFixFareWithNightSurcharge() {
    TaxiRide taxiRide = new TaxiRide();
    taxiRide.setIsNightSurcharge(true);
    taxiRide.setDistanceInMile(5L);
    Fare rideFare = new Fare();
    Long totalCharge = taxiFareCalculatorService.calculateFare(taxiRide, rideFare);

    assertNotNull(totalCharge);
    assertEquals(Long.valueOf(100), totalCharge);
  }

  @Test
  public void whenNightSurchargeFalseAndDistanceLessThan100_thenDoubleFareWithoutNightSurcharge() {
    TaxiRide taxiRide = new TaxiRide();
    taxiRide.setIsNightSurcharge(false);
    taxiRide.setDistanceInMile(50L);
    Fare rideFare = new Fare();
    Long totalCharge = taxiFareCalculatorService.calculateFare(taxiRide, rideFare);

    assertNotNull(totalCharge);
    assertEquals(Long.valueOf(170), totalCharge);
  }

  @Test
  public void whenNightSurchargeTrueAndDistanceLessThan100_thenDoubleFareWithNightSurcharge() {
    TaxiRide taxiRide = new TaxiRide();
    taxiRide.setIsNightSurcharge(true);
    taxiRide.setDistanceInMile(50L);
    Fare rideFare = new Fare();
    Long totalCharge = taxiFareCalculatorService.calculateFare(taxiRide, rideFare);

    assertNotNull(totalCharge);
    assertEquals(Long.valueOf(250), totalCharge);
  }

  @Test
  public void
      whenNightSurchargeFalseAndDistanceGreaterThan100_thenExtraPercentFareWithoutNightSurcharge() {
    TaxiRide taxiRide = new TaxiRide();
    taxiRide.setIsNightSurcharge(false);
    taxiRide.setDistanceInMile(100L);
    Fare rideFare = new Fare();
    Long totalCharge = taxiFareCalculatorService.calculateFare(taxiRide, rideFare);

    assertNotNull(totalCharge);
    assertEquals(Long.valueOf(220), totalCharge);
  }

  @Test
  public void
      whenNightSurchargeTrueAndDistanceGreaterThan100_thenExtraPercentFareWithNightSurcharge() {
    TaxiRide taxiRide = new TaxiRide();
    taxiRide.setIsNightSurcharge(true);
    taxiRide.setDistanceInMile(100L);
    Fare rideFare = new Fare();
    Long totalCharge = taxiFareCalculatorService.calculateFare(taxiRide, rideFare);

    assertNotNull(totalCharge);
    assertEquals(Long.valueOf(350), totalCharge);
  }
}
