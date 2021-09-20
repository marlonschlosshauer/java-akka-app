package example_2.actors;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import example_2.models.SwarmMessage;

import java.util.stream.IntStream;

public class Swarm extends AbstractBehavior<SwarmMessage> {
    @Override
    public Receive<SwarmMessage> createReceive() {
        return newReceiveBuilder()
                .onMessage(SwarmMessage.SwarmEcho.class, this::onEcho)
                .onMessage(SwarmMessage.SwarmResponse.class, this::onResponse)
                .build();
    }

    public Behavior<SwarmMessage> onEcho(SwarmMessage.SwarmEcho echo) {
        IntStream.range(0, echo.amount).forEach(i -> getContext().getSelf().tell(new SwarmMessage.SwarmResponse(i + " " + echo.echo)));
        return this;
    }

    public Behavior<SwarmMessage> onResponse(SwarmMessage.SwarmResponse response) {
        System.out.println(response.response);
        return this;
    }

    public Swarm(ActorContext<SwarmMessage> context) {
        super(context);
    }

    public static Behavior<SwarmMessage> create() {
        return Behaviors.setup(context -> new Swarm(context));
    }
}
