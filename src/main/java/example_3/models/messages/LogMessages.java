package example_3.models.messages;

import akka.actor.typed.ActorRef;
import example_3.models.base.Update;

public interface LogMessages {
    public class Log implements LogMessages  {
        public Update update;
        public ActorRef<LogResponse> replyTo;
        // TODO: Add timestamp?

        public Log(Update update, ActorRef<LogResponse> replyTo) {
            this.update = update;
            this.replyTo = replyTo;
        }
    }

    public class LogResponse implements  LogMessages {
        public Update update;

        public LogResponse(Update update) {
            this.update = update;
        }
    }
}
