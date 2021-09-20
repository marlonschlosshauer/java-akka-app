package example_3.models.base;

public class Incident {
    public String description;
    public Position position;
    public boolean ongoing;

    public Incident(String description, Position position, boolean ongoing) {
        this.description = description;
        this.position = position;
        this.ongoing = ongoing;
    }

    @Override
    public String toString() {
        return "Incident{" +
                "description='" + description + '\'' +
                ", position=" + position +
                '}';
    }
}
