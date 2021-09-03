package models.message;

public interface Message {
    public class Error implements Message {
        public String message;
        public Error(String message) {
            this.message = message;
        }
    }
}
