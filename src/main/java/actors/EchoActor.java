package actors;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import models.message.EchoMessage;
import models.message.LocationMessage;

//public class EchoActor extends AbstractBehavior<EchoMessage> {
public class EchoActor extends AbstractBehavior<EchoMessage> {
    public String lastMessage = "";

    public static Behavior<EchoMessage> create() {
        return Behaviors.setup(context -> new EchoActor(context));
    }

    public EchoActor(ActorContext<EchoMessage> context) {
        super(context);
    }

    @Override
    public Receive<EchoMessage> createReceive() {
        return newReceiveBuilder()
                .onMessage(EchoMessage.Echo.class, this::onEcho)
                .onMessage(EchoMessage.GetLastEchoMessage.class, this::onGetLastEchoMesage)
                .onMessage(EchoMessage.Reply.class, this::onReply)
                .onMessage(EchoMessage.Stop.class, this::onStop)
                .build();
    }

    public Behavior<EchoMessage> onEcho(EchoMessage.Echo echo) {
        this.lastMessage = echo.message;
        System.out.println("EchoActor " + echo.message);
        return this;
    }

    public Behavior<EchoMessage> onGetLastEchoMesage(EchoMessage.GetLastEchoMessage echo) {
        return this;
    }

    public Behavior<EchoMessage> onReply(EchoMessage.Reply reply) {
        reply.replyTo.tell(new EchoMessage.Echo(this.lastMessage));
        return this;
    }

    public Behavior<EchoMessage> onStop(EchoMessage.Stop stop) {
        System.out.println("Stopping");
        return Behaviors.stopped();
    }
}
