package example_3.actors;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import example_3.models.base.Incident;
import example_3.models.messages.IncidentMessage;

public class IncidentActor extends AbstractBehavior<IncidentMessage> {
    private long reports;
    private Incident incident;

    public IncidentActor(ActorContext<IncidentMessage> context) {
        super(context);
    }

    public static Behavior<IncidentMessage> create() {
        return Behaviors.setup(context -> new IncidentActor(context));
    }

    @Override
    public Receive<IncidentMessage> createReceive() {
        return newReceiveBuilder()
                .onMessage(IncidentMessage.ReportIncident.class, this::onReportIncident)
                .onMessage(IncidentMessage.UpdateIncident.class, this::onUpdateIncident)
                .onMessage(IncidentMessage.CloseIncidents.class, this::onCloseIncident)
                .build();
    }

    private void updateIncident(Incident incident) {
        this.incident = incident;
        reports++;
    }

    public Behavior<IncidentMessage> onReportIncident(IncidentMessage.ReportIncident report) {
        updateIncident(report.incident);
        return this;
    }

    public Behavior<IncidentMessage> onUpdateIncident(IncidentMessage.UpdateIncident report) {
        updateIncident(report.incident);
        return this;
    }

    public Behavior<IncidentMessage> onCloseIncident(IncidentMessage.CloseIncidents close) {
        return Behaviors.stopped();
    }
}
