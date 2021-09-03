package models.message;

import models.base.Location;

public interface EntryPointMessage {
    public class AddLocation extends Location implements EntryPointMessage {}
}
