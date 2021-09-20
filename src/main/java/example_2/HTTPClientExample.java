package example_2;

import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.AttributeKey;
import akka.http.javadsl.model.HttpRequest;

public class HTTPClientExample {
    public static void run() {
        final ActorSystem system = ActorSystem.create();

        AttributeKey<String> userId = AttributeKey.create("user-id", String.class);

        Http.get(system).singleRequest(
                // Impossible to send Body? Neither Attribute nor Entitiy are Body Intuitive framework Lightbend!
                HttpRequest.POST("http://localhost:5000/api/product")
                        .withEntity("Hello from Entity")
                        .addAttribute(userId, "Hello from Attribute"));

        try { System.in.read(); } catch (Exception e) {}
    }
}
