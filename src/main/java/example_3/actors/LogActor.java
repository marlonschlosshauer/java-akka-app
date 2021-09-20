package example_3.actors;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import example_3.models.messages.LogMessages;

public class LogActor extends AbstractBehavior<LogMessages> {
    public long totalLogs = 0;
    public long lastLog = 0;

    public LogActor(ActorContext<LogMessages> context) {
        super(context);
    }

    public static Behavior<LogMessages> create() {
        return Behaviors.setup(context -> new LogActor(context));
    }

    @Override
    public Receive<LogMessages> createReceive() {
        return newReceiveBuilder().onMessage(LogMessages.Log.class, this::onLog).build();
    }

    public Behavior<LogMessages> onLog(LogMessages.Log log) {
        totalLogs += 1;
        lastLog = System.currentTimeMillis() / 1000L;

        // TODO: Log to DB, real-time analytics, health service
        // TODO: Give LogResponse purpose
        System.out.println("Total: " + totalLogs + ", last: " + lastLog);
        log.replyTo.tell(new LogMessages.LogResponse(log.update));
        return this;
    }

}
