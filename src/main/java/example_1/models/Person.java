package example_1.models;

import akka.actor.typed.ActorRef;

public class Person {
    public long id = -1;
    public String name;
    public ActorRef<LocationMessage> location;
}
