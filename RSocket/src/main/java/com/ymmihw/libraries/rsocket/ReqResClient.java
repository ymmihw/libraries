package com.ymmihw.libraries.rsocket;

import static com.ymmihw.libraries.rsocket.support.Constants.ERROR_MSG;
import static com.ymmihw.libraries.rsocket.support.Constants.TCP_PORT;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;

public class ReqResClient {

  private final RSocket socket;

  public ReqResClient() {
    this.socket = RSocketFactory.connect()
        .transport(TcpClientTransport.create("localhost", TCP_PORT)).start().block();
  }

  public String callBlocking(String string) {
    return socket.requestResponse(DefaultPayload.create(string)).map(Payload::getDataUtf8)
        .onErrorReturn(ERROR_MSG).block();
  }

  public void dispose() {
    this.socket.dispose();
  }
}
