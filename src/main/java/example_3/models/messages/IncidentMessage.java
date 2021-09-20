package example_3.models.messages;

import akka.actor.typed.ActorRef;
import example_3.models.base.Incident;

public interface IncidentMessage {
    public class ReportIncident implements IncidentMessage {
        public Incident incident;

        public ReportIncident(Incident incident) {
            this.incident = incident;
        }
    }

    public class UpdateIncident implements IncidentMessage {
        public Incident incident;

        public UpdateIncident(Incident incident) {
            this.incident = incident;
        }
    }

    public class CloseIncidents implements IncidentMessage {}
}
