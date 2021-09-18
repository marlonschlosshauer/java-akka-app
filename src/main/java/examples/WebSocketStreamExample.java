package examples;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.ws.Message;
import akka.http.javadsl.model.ws.TextMessage;
import akka.http.javadsl.server.Route;
import akka.http.javadsl.server.AllDirectives;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Flow;
import com.google.gson.Gson;
import models.base.WebSocketStreamExample.Update;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletionStage;

import static akka.http.javadsl.server.Directives.*;

public class WebSocketStreamExample {
    private static Flow<Message, Message, NotUsed> stream (ActorSystem system) {
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

        Flow<Update, Update, NotUsed> reportIncident = Flow.of(Update.class).map(u -> u);

        return Flow.of(Message.class)
                .via(cast)
                .via(log)
                .map(u -> TextMessage.create("Successfully added update"));
    }

    public static void run() {
        final ActorSystem system = ActorSystem.create();
        final Materializer materializer = ActorMaterializer.create(system);
        final ConnectHttp host = ConnectHttp.toHost("127.0.0.1", 8080);
        final Http http = Http.get(system);

        final Route route = path("log", () -> get(() -> handleWebSocketMessages(stream(system))));

        final CompletionStage<ServerBinding> bindingCompletionStage = http.bindAndHandle(route.flow(system, materializer), host, materializer);

        bindingCompletionStage.thenAccept((binding) -> {
            final InetSocketAddress address = binding.localAddress();
            System.out.println("Akka HTTP server running at " + address.getHostString() + ":" + address.getPort());
        }).exceptionally((ex) -> {
            System.out.print("Failed to bind HTTP server: " + ex.getMessage());
            ex.fillInStackTrace();
            return null;
        });
    }
}
