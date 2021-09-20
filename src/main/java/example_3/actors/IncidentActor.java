package example_3.actors;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import example_3.models.messages.IncidentMessage;
import example_3.models.messages.LogMessages;

public class IncidentActor extends AbstractBehavior<IncidentMessage> {

    public IncidentActor(ActorContext<IncidentMessage> context) {
        super(context);
    }

    public static Behavior<IncidentMessage> create() {
        return Behaviors.setup(context -> new IncidentActor(context));
    }

    @Override
    public Receive<IncidentMessage> createReceive() {
        return newReceiveBuilder().onMessage(IncidentMessage.ReportIncident.class, this::onUpdateIncident).build();
    }

    public Behavior<IncidentMessage> onUpdateIncident(IncidentMessage.ReportIncident incident) {
        // TODO: Manage incidents
        incident.replyTo.tell(new IncidentMessage.ReportResponse(true, "yes"));
        return this;
    }
}
