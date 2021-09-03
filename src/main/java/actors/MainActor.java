package actors;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class MainActor extends AbstractBehavior {

    public MainActor(ActorContext context) {
        super(context);

    }

    public static Behavior create() {
        return Behaviors.setup(context -> new MainActor(context));
    }

    @Override
    public Receive createReceive() {
        // TODO: Add starting point
        return null;
    }
}
