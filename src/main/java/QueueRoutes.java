import akka.actor.typed.ActorSystem;
import akka.http.javadsl.server.Route;
import akka.http.javadsl.server.AllDirectives;

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
                                        {
                                            System.out.println("test: hello");
                                            system.tell("hello");
                                            return complete("GET location");
                                        }
                                        ),
                                post(() -> complete("POST location"))
                        )
                ),
                path("help", () -> get(() -> complete("Hello from akka-http :)")))
        );
    }
}
