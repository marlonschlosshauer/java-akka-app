package examples;

import actors.Swarm;
import models.message.SwarmMessage;
import akka.NotUsed;
import akka.actor.typed.ActorSystem;
import akka.stream.javadsl.Source;

public class StreamExample {

    public static void run() throws Exception {
        final var system = ActorSystem.create(Swarm.create(), "System");

        final Source<Integer, NotUsed> source = Source.range(1, 10);
        final var done = source.runForeach(i -> system.tell(new SwarmMessage.SwarmEcho("Hello", 10)), system);

        done.thenRun(() -> system.terminate());
    }
}
