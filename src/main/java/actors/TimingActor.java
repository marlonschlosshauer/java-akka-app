package actors;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import models.message.TimingMessage;

public class TimingActor extends AbstractBehavior<TimingMessage> {

    public static Behavior<TimingMessage> create() {
        return Behaviors.setup(context -> new TimingActor(context));
    }

    public TimingActor(ActorContext<TimingMessage> context) {
        super(context);
    }

    @Override
    public Receive<TimingMessage> createReceive() {
        return null;
    }
}
