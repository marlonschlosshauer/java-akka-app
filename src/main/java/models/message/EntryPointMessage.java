package models.message;

import akka.actor.typed.ActorRef;
import models.base.Location;

public interface EntryPointMessage {
    public class GetLocationCount  implements EntryPointMessage {
        public int count;
    }

    public class AddLocation extends Location implements EntryPointMessage {}

    public class GetLocation implements EntryPointMessage {
        public String id;
        public ActorRef<LocationMessage> replyTo;

        public GetLocation(ActorRef<LocationMessage> replyTo, String id) {
            this.replyTo = replyTo;
            this.id = id;
        }
    }

    public class Error implements EntryPointMessage {
        public String message;
        public Error(String message) {
            this.message = message;
        }
    }
}
