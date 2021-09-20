package example_2;

import example_1.actors.MainActor;
import example_2.actors.TimingActor;
import akka.NotUsed;
import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Source;
import akka.stream.typed.javadsl.ActorFlow;
import example_2.models.TimingMessage;

import java.time.Duration;

public class AsyncStreamExample {
    static
    class Asking {
        final String payload;
        final ActorRef<Reply> replyTo;

        public Asking(String payload, ActorRef<Reply> replyTo) {
            this.payload = payload;
            this.replyTo = replyTo;
        }
    }

    static
    class Reply {
        public final String msg;

        public Reply(String msg) {
            this.msg = msg;
        }
    }

    public static void run() {
        var system = ActorSystem.create(MainActor.create(), "main");

        Flow<Integer, Integer, NotUsed> triple = Flow.of(Integer.class).map(x -> x * 3);
        Flow<Integer, Integer, NotUsed> sum = Flow.of(Integer.class).fold(0,(acc, next) -> acc + next);

        final ActorRef<TimingMessage> timing = system.systemActorOf(TimingActor.create(), "awawaw", system.systemActorOf$default$3());

        Duration timeout = Duration.ofSeconds(1);

        Flow<Integer, Long, NotUsed> ping = ActorFlow.ask(timing, timeout, (msg, replyTo) -> new TimingMessage.Ping(msg, replyTo));

        Source.range(1, 100)
                //.groupedWithin(100, FiniteDuration.apply(1, TimeUnit.SECONDS))
                //.ask(timer, Integer.class,  Timeout.apply(3, TimeUnit.SECONDS))
/*                .via(triple)
                .take(1)
                .via(sum)
                .via(triple)
 */
                .via(ping)
                .runForeach(s -> System.out.println("Ergebnis: " +  s), system);

    }
}
