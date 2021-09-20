package example_3.models.base;

import java.util.Arrays;

public class Update {
    public String car;
    public Position position;
    public Speed speed;
    public Incident[] incidents;

    public Update() {}

    public Update(String car, Position position, Speed speed, Incident[] incidents) {
        this.car = car;
        this.position = position;
        this.speed = speed;
        this.incidents = incidents;
    }

    @Override
    public String toString() {
        return "Update{" +
                "car='" + car + '\'' +
                ", position=" + position +
                ", speed=" + speed +
                ", incidents=" + Arrays.toString(incidents) +
                '}';
    }
}
