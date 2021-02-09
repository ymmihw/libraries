package com.ymmihw.libraries;

import java.util.Date;
import com.esotericsoftware.kryo.DefaultSerializer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import lombok.Data;

@DefaultSerializer(PersonSerializer.class)
@Data
public class Person implements KryoSerializable {
  private String name = "John Doe";
  private int age = 18;
  private Date birthDate = new Date(933191282821L);

  @Override
  public void write(Kryo kryo, Output output) {
    output.writeString(name);
    output.writeLong(birthDate.getTime());
    output.writeInt(age);
  }

  @Override
  public void read(Kryo kryo, Input input) {
    name = input.readString();
    birthDate = new Date(input.readLong());
    age = input.readInt();
  }

}
