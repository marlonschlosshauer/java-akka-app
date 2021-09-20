package example_3.actors;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import example_3.models.messages.MainMessage;

public class MainActor extends AbstractBehavior<MainMessage> {
    public MainActor(ActorContext<MainMessage> context) {
        super(context);
    }

    public static Behavior<MainMessage> create() {
        return Behaviors.setup(context -> new MainActor(context));
    }

    @Override
    public Receive<MainMessage> createReceive() {
        return newReceiveBuilder().build();
    }
}
