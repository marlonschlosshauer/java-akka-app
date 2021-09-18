package models.base.WebSocketStreamExample;

public class Update {
    public String car;
    public Position position;
    public Speed speed;

    public Update(String car, Position position, Speed speed) {
        this.car = car;
        this.position = position;
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "Update{" +
                "car='" + car + '\'' +
                ", position=" + position +
                ", speed=" + speed +
                '}';
    }
}
