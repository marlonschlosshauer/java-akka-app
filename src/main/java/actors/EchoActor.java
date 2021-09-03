package actors;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class EchoActor extends AbstractBehavior<String> {

    public static Behavior<String> create() {
        return Behaviors.setup(context -> new EchoActor(context));
    }

    public EchoActor(ActorContext<String> context) {
        super(context);
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder().onMessage(String.class, this::onMessage).build();
    }

    public Behavior<String> onMessage(String message) {
        System.out.println("EchoActor " + message);
        return this;
    }
}
