package actors;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import models.message.EchoMessage;
import models.message.SumMessage;

public class SumActor extends AbstractBehavior<SumMessage> {

    public SumActor(ActorContext context) {
        super(context);
    }

    public static Behavior<SumMessage> create() {
        return Behaviors.setup(context -> new SumActor(context));
    }

    @Override
    public Receive<SumMessage> createReceive() {
        return newReceiveBuilder()
                .onMessage(SumMessage.Values.class,this::onValues)
                .build();
    }

    public Behavior<SumMessage> onValues(SumMessage.Values values) {
        values.replyTo.tell(new SumMessage.Sum(values.values.stream().reduce(0, (acc, next) -> acc + next)));
        return this;
    }
}
