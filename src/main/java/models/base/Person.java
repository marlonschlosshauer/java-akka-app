package models.base;

import akka.actor.typed.ActorRef;
import models.message.LocationMessage;

public class Person {
    public long id = -1;
    public String name;
    public ActorRef<LocationMessage> location;
}
