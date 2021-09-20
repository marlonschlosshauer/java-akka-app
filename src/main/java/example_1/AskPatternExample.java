package example_1;

import example_1.actors.EchoActor;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.AskPattern;
import example_1.models.EchoMessage;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

public class AskPatternExample {
    public static void run() {
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
