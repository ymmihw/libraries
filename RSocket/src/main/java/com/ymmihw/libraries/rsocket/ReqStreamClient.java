package com.ymmihw.libraries.rsocket;

import static com.ymmihw.libraries.rsocket.support.Constants.DATA_STREAM_NAME;
import static com.ymmihw.libraries.rsocket.support.Constants.TCP_PORT;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import reactor.core.publisher.Flux;

public class ReqStreamClient {

  private final RSocket socket;

  public ReqStreamClient() {
    this.socket =
        RSocketConnector.connectWith(TcpClientTransport.create("localhost", TCP_PORT)).block();
  }

  public Flux<Float> getDataStream() {
    return socket.requestStream(DefaultPayload.create(DATA_STREAM_NAME)).map(Payload::getData)
        .map(buf -> buf.getFloat()).onErrorReturn(null);
  }

  public void dispose() {
    this.socket.dispose();
  }
}
