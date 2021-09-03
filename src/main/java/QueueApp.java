import actors.EchoActor;
import actors.EntrypointActor;
import actors.MainActor;
import akka.actor.Props;
import akka.actor.typed.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;

import java.io.IOException;
import java.util.concurrent.CompletionStage;

public class QueueApp {
    public static void main(String[] args) throws IOException {
        // Setup actors
        var system = ActorSystem.create(EchoActor.create(), "Main");
        //var echo = system.systemActorOf(EchoActor.create(), "EchoActor", system.systemActorOf$default$3());

        // Setup REST API
        final Http http = Http.get(system);
        QueueRoutes route = new QueueRoutes(system);

        final CompletionStage<ServerBinding> binding = http.newServerAt("localhost", 3080)
                .bind(route.createRoutes());

        System.out.println("Server online at http://localhost:3080/\nPress RETURN to stop...");
        System.in.read(); // let it run until user presses return

        binding
                .thenCompose(ServerBinding::unbind) // trigger unbinding from the port
                .thenAccept(unbound -> system.terminate()); // and shutdown when done

    }
}
