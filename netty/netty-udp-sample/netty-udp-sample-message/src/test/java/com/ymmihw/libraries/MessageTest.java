package com.ymmihw.libraries;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

public class MessageTest {
  @Test
  public void test1() throws UnsupportedEncodingException {
    Message m = new Message();
    m.setMagicNumber(1234);
    m.setBodyBytes("123456789".getBytes("UTF-8"));
    System.out.println(m.getLength());
    byte[] encodeBinary = m.encodeBinary();
    System.out.println(Arrays.toString(encodeBinary));
  }


  @Test
  public void test2() throws UnsupportedEncodingException {
    Message m = new Message();
    m.setMagicNumber(Integer.MIN_VALUE);
    m.setType(Short.MIN_VALUE);
    m.setVersion(Byte.MAX_VALUE);
    m.setFormat(Byte.MIN_VALUE);
    String body = "123456789";
    m.setBodyBytes(body.getBytes("UTF-8"));
    m.setMessageId(Short.MAX_VALUE);
    byte[] bytes = m.encodeBinary();
    System.out.println(Arrays.toString(bytes));
    Message m2 = new Message();
    m2.decodeBinary(bytes);
    Assert.assertEquals(body, new String(m2.getBodyBytes(), "UTF-8"));
    Assert.assertEquals(m.getChecksum(), m2.getChecksum());
    Assert.assertEquals(m.getLength(), m2.getLength());
    Assert.assertEquals(m.getType(), m2.getType());
    Assert.assertEquals(m.getVersion(), m2.getVersion());
    Assert.assertEquals(m.getFormat(), m2.getFormat());
    Assert.assertEquals(m.getMessageId(), m2.getMessageId());
    Assert.assertEquals(m.getMagicNumber(), m2.getMagicNumber());
  }
}
