package com.ymmihw.libraries.rsocket;

import static com.ymmihw.libraries.rsocket.support.Constants.DATA_STREAM_NAME;
import static com.ymmihw.libraries.rsocket.support.Constants.TCP_PORT;
import org.reactivestreams.Publisher;
import com.ymmihw.libraries.rsocket.support.DataPublisher;
import com.ymmihw.libraries.rsocket.support.GameController;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketServer;
import io.rsocket.transport.netty.server.TcpServerTransport;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Server {
  private final Disposable server;
  private final DataPublisher dataPublisher = new DataPublisher();
  private final GameController gameController;

  public Server() {
    this.server =
        RSocketServer.create((setupPayload, reactiveSocket) -> Mono.just(new RSocketImpl()))
            .bind(TcpServerTransport.create("localhost", TCP_PORT)).block();
    this.gameController = new GameController("Server Player");
  }

  public void dispose() {
    dataPublisher.complete();
    this.server.dispose();
  }

  /**
   * RSocket implementation
   */
  private class RSocketImpl implements RSocket {

    /**
     * Handle Request/Response messages
     *
     * @param payload Message payload
     * @return payload response
     */
    @Override
    public Mono<Payload> requestResponse(Payload payload) {
      try {
        return Mono.just(payload); // reflect the payload back to the sender
      } catch (Exception x) {
        return Mono.error(x);
      }
    }

    /**
     * Handle Fire-and-Forget messages
     *
     * @param payload Message payload
     * @return nothing
     */
    @Override
    public Mono<Void> fireAndForget(Payload payload) {
      try {
        dataPublisher.publish(payload); // forward the payload
        return Mono.empty();
      } catch (Exception x) {
        return Mono.error(x);
      }
    }

    /**
     * Handle Request/Stream messages. Each request returns a new stream.
     *
     * @param payload Payload that can be used to determine which stream to return
     * @return Flux stream containing simulated measurement data
     */
    @Override
    public Flux<Payload> requestStream(Payload payload) {
      String streamName = payload.getDataUtf8();
      if (DATA_STREAM_NAME.equals(streamName)) {
        return Flux.from(dataPublisher);
      }
      return Flux.error(new IllegalArgumentException(streamName));
    }

    /**
     * Handle request for bidirectional channel
     *
     * @param payloads Stream of payloads delivered from the client
     * @return
     */
    @Override
    public Flux<Payload> requestChannel(Publisher<Payload> payloads) {
      Flux.from(payloads).subscribe(gameController::processPayload);
      Flux<Payload> channel = Flux.from(gameController);
      return channel;
    }
  }

}
