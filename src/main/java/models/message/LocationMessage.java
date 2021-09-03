package models.message;

import models.base.Person;

public interface LocationMessage {
    public class AddPerson extends Person implements LocationMessage {}
    public class NotifyPerson implements LocationMessage {}
}
