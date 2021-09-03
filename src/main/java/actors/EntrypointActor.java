package actors;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import models.base.Location;
import models.message.EntryPointMessage;
import models.message.LocationMessage;

import java.util.ArrayList;
import java.util.UUID;

public class EntrypointActor extends AbstractBehavior<EntryPointMessage> {
    ArrayList<ActorRef<LocationMessage>> locations;

    private EntrypointActor(ActorContext context) {
        super(context);
        locations = new ArrayList<>();
    }

    public static Behavior<EntryPointMessage> create() {
        return Behaviors.setup(context -> new EntrypointActor(context));
    }

    @Override
    public Receive<EntryPointMessage> createReceive() {
        return newReceiveBuilder()
                .onMessage(EntryPointMessage.AddLocation.class, this::onAddLocation)
                //.onMessage(LocationMessage.class, this)
                .build();
    }

    public Behavior<EntryPointMessage> onAddLocation(EntryPointMessage.AddLocation location) {
        // Create new EntryPoint
        this.locations.add(this.getContext().spawn(LocationActor.create(new Location(UUID.fromString(location.name).toString(), location.name)), "lol"));
        return this;
    }

    public Behavior<EntryPointMessage> onLocationMessage (LocationMessage message) {
        return this;
    }
}
