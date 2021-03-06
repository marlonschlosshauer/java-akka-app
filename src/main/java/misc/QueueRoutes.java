package misc;

import akka.actor.typed.ActorRef; import akka.actor.typed.ActorSystem;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.server.Route;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.unmarshalling.StringUnmarshallers;
import example_1.models.Location;
import example_1.models.EntryPointMessage;
import example_1.models.LocationMessage;

import static akka.actor.typed.javadsl.AskPattern.ask;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

public class QueueRoutes  extends AllDirectives{
    public ActorSystem system;

    public QueueRoutes(ActorSystem system) {
        this.system = system;
    }

    public Route createRoutes() {
        return concat(
                path("location", () ->
                        concat(
                                get(() ->
                                        parameter(StringUnmarshallers.STRING, "id", id -> {
                                            // Forward GET request to EntryPointActor::GetLocation
                                            CompletionStage<LocationMessage> location =
                                                    ask(
                                                            system,
                                                            (ActorRef<LocationMessage> replyTo) -> new EntryPointMessage.GetLocation(replyTo, id),
                                                            Duration.ofSeconds(5),
                                                            system.scheduler()
                                                    );
                                            return completeOKWithFuture(location, Jackson.marshaller());
                                        })
                                ),
                                get(() -> {
                                            system.tell("hello");
                                            return complete("GET location");
                                        }
                                ),
                                post(() ->
                                        entity(Jackson.unmarshaller(Location.class), location -> {
                                            CompletionStage<LocationMessage> l =
                                                    ask(
                                                            system,
                                                            (ActorRef<LocationMessage> replyTo) -> new EntryPointMessage.AddLocation(replyTo, location),
                                                            Duration.ofSeconds(5),
                                                            system.scheduler()
                                                    );

                                            return completeOKWithFuture(l, Jackson.marshaller());
                                        })
                                )
                        )
                ),
                path("help", () -> get(() -> complete("Hello from akka-http :)")))
        );
    }
}
