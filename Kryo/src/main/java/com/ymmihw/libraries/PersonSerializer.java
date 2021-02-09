package com.ymmihw.libraries;

import java.util.Date;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class PersonSerializer extends Serializer<Person> {

  @Override
  public void write(Kryo kryo, Output output, Person object) {
    output.writeString(object.getName());
    output.writeLong(object.getBirthDate().getTime());
  }

  @Override
  public Person read(Kryo kryo, Input input, Class<? extends Person> type) {
    Person person = new Person();
    person.setName(input.readString());
    long birthDate = input.readLong();
    person.setBirthDate(new Date(birthDate));
    person.setAge(calculateAge(birthDate));
    return person;
  }

  private int calculateAge(long birthDate) {
    // Some custom logic
    return 18;
  }

}
