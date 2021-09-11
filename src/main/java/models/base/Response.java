package models.base;

public class Response {
    private boolean wasSuccessful;

    public Response(boolean wasSuccessful) {
        this.wasSuccessful = wasSuccessful;
    }

    public boolean isWasSuccessful() {
        return wasSuccessful;
    }
}
