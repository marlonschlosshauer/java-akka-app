package example_2.models;

import akka.actor.typed.ActorRef;

public interface TimingMessage {
    public class Start implements TimingMessage {
        public int value = 0;

        public Start(int value) {
            this.value = value;
        }
    }

    public class Stop implements TimingMessage {
        public int value = 0;

        public Stop(int value) {
            this.value = value;
        }
    }

    public class Ping implements TimingMessage {
        public final Integer value;
        public final ActorRef<Long> replyTo;

        public Ping(Integer value, ActorRef<Long> replyTo) {
            this.value = value;
            this.replyTo = replyTo;
        }
    }
}
