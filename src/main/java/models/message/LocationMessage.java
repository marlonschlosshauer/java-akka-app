package models.message;

import models.base.Person;

public interface LocationMessage {
    public class AddPerson extends Person implements LocationMessage {}
    public class NotifyPerson implements LocationMessage {}

    public class Error implements LocationMessage {
        public String message;
        public Error(String message) {
            this.message = message;
        }
    }
}
