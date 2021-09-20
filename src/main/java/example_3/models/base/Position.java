package example_3.models.base;

public class Position {
    public long lat;
    public long lng;

    public Position(long lat, long lng) {
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Position{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
