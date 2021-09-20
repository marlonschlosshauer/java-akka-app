package example_1.models;

import akka.actor.typed.ActorRef;

public interface EchoMessage {
    public class Echo implements EchoMessage {
        public String message;

        public Echo(String message) {
            this.message = message;
        }
    }

    public class GetLastEchoMessage implements EchoMessage {}

    public class Stop implements EchoMessage {};

    public class Reply implements  EchoMessage {
        public ActorRef<EchoMessage> replyTo;

        public Reply(ActorRef<EchoMessage> replyTo) {
            this.replyTo = replyTo;
        }
    }
}
