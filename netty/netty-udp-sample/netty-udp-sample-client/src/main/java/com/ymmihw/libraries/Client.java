package com.ymmihw.libraries;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.internal.SocketUtils;

/**
 * A UDP broadcast client that asks for a quote of the moment (QOTM) to
 * {@link QuoteOfTheMomentServer}.
 *
 * Inspired by
 * <a href="http://docs.oracle.com/javase/tutorial/networking/datagrams/clientServer.html">the
 * official Java tutorial</a>.
 */
public final class Client {

  static final int PORT = 7686;

  public static void main(String[] args) throws Exception {

    EventLoopGroup group = new NioEventLoopGroup();
    try {
      Bootstrap b = new Bootstrap();
      b.group(group).channel(NioDatagramChannel.class).handler(new ChannelHandlerAdapter() {});

      Channel ch = b.bind(0).sync().channel();

      // Broadcast the QOTM request to port 8080.
      // ch.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("QOTM?", CharsetUtil.UTF_8),
      // SocketUtils.socketAddress("127.0.0.1", PORT))).sync();


      Message m = new Message();
      m.setMagicNumber(Integer.MIN_VALUE);
      m.setType(Short.MIN_VALUE);
      m.setVersion(Byte.MAX_VALUE);
      m.setFormat(Byte.MIN_VALUE);
      String body = "123456789";
      m.setBodyBytes(body.getBytes("UTF-8"));
      m.setMessageId(Short.MAX_VALUE);

      ch.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(m.encodeBinary()),
          SocketUtils.socketAddress("127.0.0.1", PORT))).sync();
      // QuoteOfTheMomentClientHandler will close the DatagramChannel when a
      // response is received. If the channel is not closed within 5 seconds,
      // print an error message and quit.
    } finally {
      group.shutdownGracefully();
    }
  }
}
