package com.ymmihw.libraries.avro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AvroSerealizerDeSerealizerUnitTest {

  AvroSerealizer serealizer;
  AvroDeSerealizer deSerealizer;
  AvroHttpRequest request;

  @BeforeEach
  public void setUp() throws Exception {
    serealizer = new AvroSerealizer();
    deSerealizer = new AvroDeSerealizer();

    ClientIdentifier clientIdentifier = ClientIdentifier.newBuilder().setHostName("localhost")
        .setIpAddress("255.255.255.0").build();

    List<CharSequence> employees = new ArrayList<>();
    employees.add("James");
    employees.add("Alice");
    employees.add("David");
    employees.add("Han");

    request = AvroHttpRequest.newBuilder().setRequestTime(01l).setActive(Active.YES)
        .setClientIdentifier(clientIdentifier).setEmployeeNames(employees).build();
  }

  @AfterEach
  public void tearDown() throws Exception {}

  @Test
  public void whenSerializedUsingJSONEncoder_thenObjectGetsSerialized() {
    byte[] data = serealizer.serealizeAvroHttpRequestJSON(request);
    assertTrue(Objects.nonNull(data));
    assertTrue(data.length > 0);
  }

  @Test
  public void whenSerializedUsingBinaryEncoder_thenObjectGetsSerialized() {
    byte[] data = serealizer.serealizeAvroHttpRequestBinary(request);
    assertTrue(Objects.nonNull(data));
    assertTrue(data.length > 0);
  }

  @Test
  public void whenDeserializeUsingJSONDecoder_thenActualAndExpectedObjectsAreEqual() {
    byte[] data = serealizer.serealizeAvroHttpRequestJSON(request);
    AvroHttpRequest actualRequest = deSerealizer.deSerealizeAvroHttpRequestJSON(data);
    assertEquals(actualRequest, request);
    assertEquals(actualRequest.getRequestTime(), request.getRequestTime());
  }

  @Test
  public void whenDeserializeUsingBinaryecoder_thenActualAndExpectedObjectsAreEqual() {
    byte[] data = serealizer.serealizeAvroHttpRequestBinary(request);
    AvroHttpRequest actualRequest = deSerealizer.deSerealizeAvroHttpRequestBinary(data);
    assertEquals(actualRequest, request);
    assertEquals(actualRequest.getRequestTime(), request.getRequestTime());
  }

}

