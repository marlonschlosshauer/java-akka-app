package example_1.models;

import akka.actor.typed.ActorRef;

public class Location implements LocationMessage {
    public String id;
    public String name;
    public ActorRef<LocationMessage> ref;

    public Location() {}

    public Location(Location location) {
        this.id = location.id;
        this.name = location.name;
        this.ref = location.ref;
    }

    public Location(String id, String name) {
        this.id = id;
        this.name = name;
    }
}

