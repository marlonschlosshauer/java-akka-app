package example_2.models;

import akka.actor.typed.ActorRef;

import java.util.List;

public interface SumMessage {

    public class Sum implements SumMessage {
        public Integer sum;

        public Sum(Integer sum) {
            this.sum = sum;
        }
    }

    public class Values implements SumMessage {
        public List<Integer> values;
        public ActorRef<Sum> replyTo;

        public Values(List<Integer> values, ActorRef<Sum> replyTo) {
            this.values = values;
            this.replyTo = replyTo;
        }
    }
}
