import actors.EchoActor;
import actors.EntrypointActor;
import actors.MainActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.AskPattern;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.pattern.Patterns;
import akka.util.Timeout;
import models.message.EchoMessage;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.CompletionStage;

public class QueueApp {
    public static void main(String[] args) throws IOException {
        var system = ActorSystem.create(EchoActor.create(), "Main");
        var echo = system.systemActorOf(EchoActor.create(), "Lol", system.systemActorOf$default$3());
        echo.tell(new EchoMessage.Echo("Hello from Akka"));

        CompletionStage<EchoMessage> response = AskPattern.ask(
                echo,
                replyTo -> new EchoMessage.Reply(replyTo),
                Duration.ofSeconds(3),
                system.scheduler());

        response.whenComplete((reply, failure) -> System.out.println("Message: " + ((EchoMessage.Echo) reply).message));

        echo.tell(new EchoMessage.Echo("Hello from Akka"));
        echo.tell(new EchoMessage.Stop());
        echo.tell(new EchoMessage.Echo("This should not be printed"));

    }
}
