package com.ymmihw.libraries;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * A UDP server that responds to the QOTM (quote of the moment) request to a
 * {@link QuoteOfTheMomentClient}.
 *
 * Inspired by
 * <a href="http://docs.oracle.com/javase/tutorial/networking/datagrams/clientServer.html">the
 * official Java tutorial</a>.
 */
public final class Server {

  private static final int PORT = 7686;

  public static void main(String[] args) throws Exception {
    EventLoopGroup group = new NioEventLoopGroup();
    try {
      Bootstrap b = new Bootstrap();
      b.group(group).channel(NioDatagramChannel.class).handler(new ServerHandler());

      b.bind(PORT).sync().channel().closeFuture().await();
    } finally {
      group.shutdownGracefully();
    }
  }
}
