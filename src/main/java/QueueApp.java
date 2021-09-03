import actors.EntrypointActor;
import actors.MainActor;
import akka.actor.Props;
import akka.actor.typed.ActorSystem;

public class QueueApp {
    public static void main(String[] args) {
        ActorSystem.create(EntrypointActor.create(), "Main");
    }
}
