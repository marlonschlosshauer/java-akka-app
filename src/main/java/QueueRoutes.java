import actors.LocationActor;
import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.ActorContext;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.server.Route;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.unmarshalling.StringUnmarshallers;
import models.base.Location;
import models.message.EntryPointMessage;
import models.message.LocationMessage;

import static akka.actor.typed.javadsl.AskPattern.ask;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

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
                                            location.id = UUID.fromString(location.name).toString();
                                            system.systemActorOf(LocationActor.create(location), location.id, system.systemActorOf$default$3());
                                            return complete(String.format("Successfully added Location with id %s",location.id));
                                        })
                                )
                        )
                ),
                path("help", () -> get(() -> complete("Hello from akka-http :)")))
        );
    }
}
