package example_1.models;

import akka.actor.typed.ActorRef;

public interface EntryPointMessage {
    public class GetLocationCount implements EntryPointMessage {
        public int count;
    }

    public class AddLocation extends Location implements EntryPointMessage {
        public ActorRef<LocationMessage> replyTo;

        public AddLocation(ActorRef<LocationMessage> replyTo, Location location) {
            super(location);
            this.replyTo = replyTo;
        }
    }

    public class GetLocation implements EntryPointMessage {
        public ActorRef<LocationMessage> replyTo;
        public String id;

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
