package example_3.models.base;

public class Incident {
    public String description;
    public Position position;

    public Incident(String description, Position position) {
        this.description = description;
        this.position = position;
    }

    @Override
    public String toString() {
        return "Incident{" +
                "description='" + description + '\'' +
                ", position=" + position +
                '}';
    }
}
