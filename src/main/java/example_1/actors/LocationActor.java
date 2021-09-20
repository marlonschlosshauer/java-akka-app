package example_1.actors;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import example_1.models.Location;
import example_1.models.LocationMessage;

public class LocationActor extends AbstractBehavior<LocationMessage> {
    public Location location;

    private LocationActor(ActorContext context, Location location) {
        super(context);
        this.location = location;
    }

    public static Behavior<LocationMessage> create(Location location) {
        return Behaviors.setup(context -> new LocationActor(context, location));
    }

    @Override
    public Receive<LocationMessage> createReceive() {
        return newReceiveBuilder()
                .onMessage(LocationMessage.AddPerson.class, this::onAddPerson)
                .build();
    }

    public Behavior<LocationMessage> onAddPerson (LocationMessage.AddPerson person) {
        return this;
    }
}
