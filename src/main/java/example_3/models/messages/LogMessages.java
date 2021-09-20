package example_3.models.messages;

import akka.actor.typed.ActorRef;
import example_3.models.base.Update;

import java.util.List;

public interface LogMessages {
    public class Log implements LogMessages  {
        public List<Update> update;
        public ActorRef<LogResponse> replyTo;
        // TODO: Add timestamp?

        public Log(List<Update> update, ActorRef<LogResponse> replyTo) {
            this.update = update;
            this.replyTo = replyTo;
        }
    }

    public class LogResponse implements  LogMessages {
        public List<Update> update;

        public LogResponse(List<Update> update) {
            this.update = update;
        }
    }
}
