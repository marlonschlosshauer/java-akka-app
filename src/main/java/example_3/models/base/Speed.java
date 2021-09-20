package example_3.models.base;

public class Speed {
    public float value;
    public String unit;

    public Speed(float value, String unit) {
        this.value = value;
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Speed{" +
                "speed=" + value +
                ", unit='" + unit + '\'' +
                '}';
    }
}
