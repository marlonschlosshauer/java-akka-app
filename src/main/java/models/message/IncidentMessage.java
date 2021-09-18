package models.message;

import akka.actor.typed.ActorRef;
import models.base.WebSocketStreamExample.Incident;

public interface IncidentMessage {
    public class ReportIncident implements IncidentMessage {
        public Incident incident;
        public ActorRef<ReportAnswer> replyTo;

        public ReportIncident(Incident incident, ActorRef<ReportAnswer> replyTo) {
            this.incident = incident;
            this.replyTo = replyTo;
        }
    }

    public class ReportAnswer implements IncidentMessage {
        public boolean success;
        public String description;

        public ReportAnswer(boolean success, String description) {
            this.success = success;
            this.description = description;
        }
    }
}
