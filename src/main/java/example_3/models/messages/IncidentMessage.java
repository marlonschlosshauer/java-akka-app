package example_3.models.messages;

import akka.actor.typed.ActorRef;
import example_3.models.base.Incident;

public interface IncidentMessage {
    public class ReportIncident implements IncidentMessage {
        public Incident incident;
        public ActorRef<ReportResponse> replyTo;

        public ReportIncident(Incident incident, ActorRef<ReportResponse> replyTo) {
            this.incident = incident;
            this.replyTo = replyTo;
        }
    }

    public class ReportResponse implements IncidentMessage {
        public boolean success;
        public String description;

        public ReportResponse(boolean success, String description) {
            this.success = success;
            this.description = description;
        }
    }
}
