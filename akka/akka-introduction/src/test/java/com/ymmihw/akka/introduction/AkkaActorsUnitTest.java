package com.ymmihw.akka.introduction;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.testkit.TestKit;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import scala.util.Failure;

public class AkkaActorsUnitTest {

  private static ActorSystem system = null;

  @BeforeAll
  public static void setup() {
    system = ActorSystem.create("test-system");
  }

  @AfterAll
  public static void teardown() {
    TestKit.shutdownActorSystem(system, Duration.apply(1000, TimeUnit.MILLISECONDS), true);
    system = null;
  }

  @Test
  public void givenAnActor_sendHimAMessageUsingTell() {

    final TestKit probe = new TestKit(system);
    ActorRef myActorRef = probe.childActorOf(Props.create(MyActor.class));
    myActorRef.tell("printit", probe.testActor());

    probe.expectMsg("Got Message");
  }

  @Test
  public void givenAnActor_sendHimAMessageUsingAsk()
      throws ExecutionException, InterruptedException {

    final TestKit probe = new TestKit(system);
    ActorRef wordCounterActorRef = probe.childActorOf(Props.create(WordCounterActor.class));

    Future<Object> future =
        Patterns.ask(wordCounterActorRef, new WordCounterActor.CountWords("this is a text"), 1000);

    future.onComplete(a -> {
      Integer numberOfWords = (Integer) a.get();
      assertTrue(4 == numberOfWords, "The actor should count 4 words");
      return a.get();
    }, ExecutionContext.global());

    while (!future.isCompleted()) {
      TimeUnit.MILLISECONDS.sleep(10);
    }
  }

  @Test
  public void givenAnActor_whenTheMessageIsNull_respondWithException() throws InterruptedException {
    final TestKit probe = new TestKit(system);
    ActorRef wordCounterActorRef = probe.childActorOf(Props.create(WordCounterActor.class));

    Future<Object> future =
        Patterns.ask(wordCounterActorRef, new WordCounterActor.CountWords(null), 1000);


    future.onComplete(a -> {
      assertTrue(a.isFailure());
      IllegalArgumentException e = (IllegalArgumentException) ((Failure<?>) a).exception();
      assertTrue(e.getMessage().contains("The text to process can't be null!"),
          "Invalid error message");
      return a.get();
    }, ExecutionContext.global());

    while (!future.isCompleted()) {
      TimeUnit.MILLISECONDS.sleep(10);
    }

  }

  @Test
  public void givenAnAkkaSystem_countTheWordsInAText() {
    ActorSystem system = ActorSystem.create("test-system");
    ActorRef myActorRef = system.actorOf(Props.create(MyActor.class), "my-actor");
    myActorRef.tell("printit", null);

    ActorRef readingActorRef = system.actorOf(ReadingActor.props(TEXT), "readingActor");
    readingActorRef.tell(new ReadingActor.ReadLines(), ActorRef.noSender());
  }

  private static String TEXT =
      "Lorem Ipsum is simply dummy text\n" + "of the printing and typesetting industry.\n"
          + "Lorem Ipsum has been the industry's standard dummy text\n"
          + "ever since the 1500s, when an unknown printer took a galley\n"
          + "of type and scrambled it to make a type specimen book.\n"
          + " It has survived not only five centuries, but also the leap\n"
          + "into electronic typesetting, remaining essentially unchanged.\n"
          + " It was popularised in the 1960s with the release of Letraset\n"
          + " sheets containing Lorem Ipsum passages, and more recently with\n"
          + " desktop publishing software like Aldus PageMaker including\n"
          + "versions of Lorem Ipsum.";

}
