package example_3;

import akka.NotUsed;
import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.ws.Message;
import akka.http.javadsl.model.ws.TextMessage;
import akka.http.javadsl.server.Route;
import akka.stream.javadsl.Flow;
import akka.stream.typed.javadsl.ActorFlow;
import com.google.gson.Gson;
import example_3.actors.IncidentManagerActor;
import example_3.actors.LogActor;
import example_3.actors.MainActor;
import example_3.models.base.Incident;
import example_3.models.base.Update;
import example_3.models.messages.IncidentManagerMessage;
import example_3.models.messages.LogMessages;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionStage;

import static akka.http.javadsl.server.Directives.*;

public class WebSocketStreamExample {
    private static Flow<Message, Message, NotUsed> stream (ActorSystem system) {
        final ActorRef<LogMessages> logger = system.systemActorOf(LogActor.create(), "LogActor", system.systemActorOf$default$3());
        final ActorRef<IncidentManagerMessage> reporter = system.systemActorOf(IncidentManagerActor.create(system), "IncidentManager", system.systemActorOf$default$3());
        final Gson translator = new Gson();

        Flow<Update, Update, NotUsed> log = Flow.of(Update.class).map(x -> {
            System.out.println("Received Message: ");
            System.out.println(x.toString());
            System.out.println("------------------");
            return x;
        });

        Flow<Message, Update, NotUsed> cast = Flow.of(Message.class).map(u ->
        {
            Update result = null;
            try {
                result = translator.fromJson(u.asTextMessage().getStrictText(), Update.class);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return result;
        });

        Flow<Incident, IncidentManagerMessage.ReportResponse, NotUsed> reportIncidents =
                ActorFlow.ask(reporter, Duration.ofSeconds(3), (i, replyTo) -> new IncidentManagerMessage.ReportIncident(i, replyTo));

        Flow<List<Update>, LogMessages.LogResponse, NotUsed> logUpdate =
                ActorFlow.ask(logger, Duration.ofSeconds(3), (update, replyTo) -> new LogMessages.Log(update, replyTo));

        return Flow.of(Message.class)
                // Message -> Update
                .via(cast)
                // Throttle requests
                .groupedWithin(1000, Duration.ofSeconds(1))
                // Log Update to DB, Analytics, System Logs
                .via(logUpdate)
                // Unpack Response
                .map(r -> r.update)
                // Unfold Update[] -> Incidents[] -> ...Incidents
                .mapConcat((List<Update> u) -> u)
                .mapConcat((Update u) -> Arrays.asList(u.incidents))
                // Handle reported Incidents
                .via(reportIncidents)
                // Answer request from User
                .map(u -> TextMessage.create("Successfully added update"));
    }

    public static void run() {
        final ActorSystem system = ActorSystem.create(MainActor.create(), "MainActor");
        final Http http = Http.get(system);

        final Route route = path("log", () -> get(() -> handleWebSocketMessages(stream(system))));

        final CompletionStage<ServerBinding> binding =
                http.newServerAt("localhost", 8080)
                        .bind(route);

        System.out.println("Server online at http://localhost:8080/\nPress RETURN to stop...");

        try {
            System.in.read(); // let it run until user presses return
            binding
                    .thenCompose(ServerBinding::unbind) // trigger unbinding from the port
                    .thenAccept(unbound -> system.terminate()); // and shutdown when done
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
