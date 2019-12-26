package com.ymmihw.libraries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@ToString
public class Message {

  private static final int HEADER_LENGTH = 16;

  private int magicNumber;
  private short type;
  private byte version;
  private byte format;
  private short messageId;
  private int length;
  private short checksum;
  private byte[] bodyBytes;

  public void decodeBinary(byte[] bytes) {
    this.magicNumber = decodeInteger(Arrays.copyOfRange(bytes, 0, 4));
    this.type = decodeShort(Arrays.copyOfRange(bytes, 4, 6));
    this.version = bytes[6];
    this.format = bytes[7];
    this.messageId = decodeShort(Arrays.copyOfRange(bytes, 8, 10));
    this.length = decodeInteger(Arrays.copyOfRange(bytes, 10, 14));
    this.checksum = decodeShort(Arrays.copyOfRange(bytes, 14, 16));
    this.bodyBytes = Arrays.copyOfRange(bytes, HEADER_LENGTH, length);
  }

  public byte[] encodeBinary() {
    List<byte[]> list = new ArrayList<>();
    list.add(encodeBinary(magicNumber));
    list.add(encodeBinary(type));
    list.add(new byte[] {version, format});
    list.add(encodeBinary(messageId));
    list.add(encodeBinary(length));
    list.add(encodeBinary(checksum));
    list.add(bodyBytes);

    byte[] value = new byte[length];

    int destPos = 0;
    for (byte[] e : list) {
      System.arraycopy(e, 0, value, destPos, e.length);
      destPos += e.length;
    }

    if (log.isDebugEnabled()) {
      StringBuilder sb = new StringBuilder();
      for (byte e : value) {
        sb.append(String.format("%02X,", e));
      }
      log.debug("decode result is {}", sb.toString());
    }

    return value;

  }

  public void setBodyBytes(byte[] bodyBytes) {
    this.bodyBytes = bodyBytes;
    this.length = HEADER_LENGTH + bodyBytes.length;
    this.checksum = (short) CRC16.crc(bodyBytes);
  }

  private static short decodeShort(byte[] bytes) {
    int val;
    val = bytes[0] & 0xFF;
    val <<= 8;
    val |= bytes[1] & 0xFF;
    return (short) val;
  }

  private static int decodeInteger(byte[] bytes) {
    int value;
    value = bytes[0] & 0xFF;
    value <<= 8;
    value |= bytes[1] & 0xFF;
    value <<= 8;
    value |= bytes[2] & 0xFF;
    value <<= 8;
    value |= bytes[3] & 0xFF;
    return value;
  }

  private static byte[] encodeBinary(short value) {
    byte[] bytes = new byte[2];
    bytes[0] = (byte) ((value >> 8) & 0xFF);
    bytes[1] = (byte) (value & 0xFF);
    return bytes;
  }


  private static byte[] encodeBinary(int value) {
    byte[] bytes = new byte[4];
    bytes[0] = (byte) ((value >> 24) & 0xFF);
    bytes[1] = (byte) ((value >> 16) & 0xFF);
    bytes[2] = (byte) ((value >> 8) & 0xFF);
    bytes[3] = (byte) (value & 0xFF);
    return bytes;
  }
}
