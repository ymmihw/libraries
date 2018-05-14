package com.ymmihw.libraries.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.logging.Logger;

public class ChannelHandlerA extends ChannelInboundHandlerAdapter {

  private Logger logger = Logger.getLogger(getClass().getName());

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    throw new Exception("Ooops");
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    logger.info("Exception Occurred in ChannelHandler A");
    ctx.fireExceptionCaught(cause);
  }
}
