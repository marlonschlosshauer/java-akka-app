package example_2;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Source;

public class SimpleStreamMeasurementExample {
    public static void run() {
        var system = ActorSystem.create();

        Flow<Integer, Integer, NotUsed> triple = Flow.of(Integer.class).map(x -> x * 3);
        Flow<Integer, Integer, NotUsed> sum = Flow.of(Integer.class).fold(0,(acc, next) -> acc + next);
        Flow<Integer, Integer, NotUsed> ping = Flow.of(Integer.class).log("Logging Flow", x -> x);


        final Source<Integer, NotUsed> source = Source.range(1, 100);

        Source.range(1, 100)
                .via(triple)
                .take(1)
                .via(sum)
                .via(triple)
                .runForeach(s -> System.out.println("Ergebnis: " +  s), ActorSystem.create());

    }
}
