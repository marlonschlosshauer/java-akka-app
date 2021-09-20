package example_2;

import example_1.actors.MainActor;
import example_2.actors.SumActor;
import akka.NotUsed;
import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Source;
import akka.stream.typed.javadsl.ActorFlow;
import example_2.models.SumMessage;

import java.time.Duration;
import java.util.List;

public class ActorStreamInteropExample {
    public static void run() {
        var system = ActorSystem.create(MainActor.create(), "main");
        final ActorRef<SumMessage> sumActor = system.systemActorOf(SumActor.create(), "sumActor", system.systemActorOf$default$3());

        Flow<List<Integer>, SumMessage.Sum, NotUsed> sum = ActorFlow.ask(sumActor, Duration.ofSeconds(1), (values, replyTo) -> new SumMessage.Values(values, replyTo));

        Source.range(1, 100)
                .groupedWithin(50, Duration.ofSeconds(1))
                .via(sum)
                .map(s -> s.sum)
                .runForeach(s -> System.out.println("Summierung: " +  s), system);
    }
}
