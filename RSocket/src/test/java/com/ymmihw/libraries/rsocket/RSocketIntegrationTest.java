package com.ymmihw.libraries.rsocket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.Disposable;

public class RSocketIntegrationTest {

  private static final Logger LOG = LoggerFactory.getLogger(RSocketIntegrationTest.class);

  private static Server server;

  public RSocketIntegrationTest() {}

  @BeforeAll
  public static void setUpClass() {
    server = new Server();
  }

  @AfterAll
  public static void tearDownClass() {
    server.dispose();
  }

  @Test
  public void whenSendingAString_thenRevceiveTheSameString() {
    ReqResClient client = new ReqResClient();
    String string = "Hello RSocket";

    assertEquals(string, client.callBlocking(string));

    client.dispose();
  }

  @Test
  public void whenSendingStream_thenReceiveTheSameStream() {
    // create the client that pushes data to the server and start sending
    FireNForgetClient fnfClient = new FireNForgetClient();
    // create a client to read a stream from the server and subscribe to events
    ReqStreamClient streamClient = new ReqStreamClient();

    // get the data that is used by the client
    List<Float> data = fnfClient.getData();
    // create a place to count the results
    List<Float> dataReceived = new ArrayList<>();

    // assert that the data received is the same as the data sent
    Disposable subscription = streamClient.getDataStream().index().subscribe(tuple -> {
      assertEquals(data.get(tuple.getT1().intValue()), tuple.getT2(), "Wrong value");
      dataReceived.add(tuple.getT2());
    }, err -> LOG.error(err.getMessage()));

    // start sending the data
    fnfClient.sendData();

    // wait a short time for the data to complete then dispose everything
    try {
      Thread.sleep(500);
    } catch (Exception x) {
    }
    subscription.dispose();
    fnfClient.dispose();

    // verify the item count
    assertEquals(data.size(), dataReceived.size(), "Wrong data count received");
  }

  // @Test
  // FIXME, work on RC5, why?
  public void whenRunningChannelGame_thenLogTheResults() {
    ChannelClient client = new ChannelClient();
    client.playGame();
    client.dispose();
  }

}
