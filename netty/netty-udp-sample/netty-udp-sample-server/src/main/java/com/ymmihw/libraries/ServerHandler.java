package com.ymmihw.libraries;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

public class ServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
    ByteBuf content = msg.content();
    byte[] bytes = new byte[content.readableBytes()];
    content.readBytes(bytes);
    Message m = new Message();
    m.decodeBinary(bytes);
    System.out.println(m);
    System.out.println(CRC16.crc(m.getBodyBytes()));
  }
}
