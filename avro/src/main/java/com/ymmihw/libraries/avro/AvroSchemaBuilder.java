package com.ymmihw.libraries.avro;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;

public class AvroSchemaBuilder {

  public static void main(String[] args) {
    Schema clientIdentifier =
        SchemaBuilder.record("ClientIdentifier").namespace("com.ymmihw.libraries.avro").fields()
            .requiredString("hostName").requiredString("ipAddress").endRecord();

    Schema avroHttpRequest =
        SchemaBuilder.record("AvroHttpRequest").namespace("com.ymmihw.libraries.avro").fields()
            .requiredLong("requestTime").name("clientIdentifier").type(clientIdentifier).noDefault()
            .name("employeeNames").type().array().items().stringType().noDefault().name("active")
            .type().enumeration("Active").symbols("YES", "NO").noDefault().endRecord();
    System.out.println(avroHttpRequest.toString());
  }
}
