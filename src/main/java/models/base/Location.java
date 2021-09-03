package models.base;

import models.message.LocationMessage;

public class Location implements LocationMessage {
    public String id;
    public String name;

    public Location() {}

    public Location(String id, String name) {
        this.id = id;
        this.name = name;
    }
}

