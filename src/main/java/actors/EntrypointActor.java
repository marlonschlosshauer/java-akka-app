package actors;

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
    ArrayList<Location> locations;

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
                .onMessage(EntryPointMessage.GetLocation.class, this::onGetLocation)
                .build();
    }

    public Behavior<EntryPointMessage> onAddLocation(EntryPointMessage.AddLocation location) {
        // Create Location
        Location result = new Location(UUID.randomUUID().toString(), location.name);
        // Start Actor for Location
        result.ref = this.getContext().spawn(LocationActor.create(result), result.id);
        // Add Actor to list
        this.locations.add(result);
        // Reply with Location obj
        location.replyTo.tell(result);
        return this;
    }

    public Behavior<EntryPointMessage> onGetLocation(EntryPointMessage.GetLocation location) {
        Location result = null;

        // Find location
        for(Location l : this.locations) {
            if (l.id.equals(location.id)) {
                result = l;
                break;
            }
        }

        // Send reply to request
        if (result != null) {
            location.replyTo.tell(result);
        } else {
            location.replyTo.tell(new LocationMessage.Error("Couldn't find"));
        }

        return this;
    }
}
