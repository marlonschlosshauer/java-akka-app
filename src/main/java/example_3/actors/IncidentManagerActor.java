package example_3.actors;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import example_3.models.base.Incident;
import example_3.models.messages.IncidentManagerMessage;
import example_3.models.messages.IncidentMessage;

import java.util.HashMap;

public class IncidentManagerActor extends AbstractBehavior<IncidentManagerMessage> {
    public HashMap<Long, ActorRef<IncidentMessage>> incidents = new HashMap<Long, ActorRef<IncidentMessage>>();
    private ActorSystem system;

    private long getIncidentNumber(Incident incident) {
        // TODO:
        return 12L;
    }

    public IncidentManagerActor(ActorContext<IncidentManagerMessage> context, ActorSystem system) {
        super(context);
    }

    public static Behavior<IncidentManagerMessage> create(ActorSystem system) {
        return Behaviors.setup(context -> new IncidentManagerActor(context, system));
    }

    @Override
    public Receive<IncidentManagerMessage> createReceive() {
        return newReceiveBuilder().onMessage(IncidentManagerMessage.ReportIncident.class, this::onReportIncident).build();
    }

    public Behavior<IncidentManagerMessage> onReportIncident(IncidentManagerMessage.ReportIncident report) {
        // Calculate id from position of incident
        long id = getIncidentNumber(report.incident);

        // If incident not exists
        if (!incidents.containsKey(id)) {
            // Create actor and add to collection
            ActorRef<IncidentMessage> handler =  system.systemActorOf(IncidentActor.create(), "IncidentActor" + id, system.systemActorOf$default$3());
            incidents.put(id, handler);
            handler.tell(new IncidentMessage.ReportIncident(report.incident));
        } else {
            // Else send update to incident (congestion level, issue, etc.)
            ActorRef<IncidentMessage> target = incidents.get(id);

            if (report.incident.ongoing) {
                target.tell(new IncidentMessage.CloseIncidents());
                incidents.remove(target);
            } else {
                target.tell(new IncidentMessage.UpdateIncident(report.incident));
            }
        }

        report.replyTo.tell(new IncidentManagerMessage.ReportResponse(true, "yes"));
        return this;
    }
}
