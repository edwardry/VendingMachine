public class Coin {
    private Double mass;
    private Double diameter;
    private Double value;
    public static final Double VALUELESS = 0.0;

    public Coin(Double mass, Double diameter) {
        this.mass = mass;
        this.diameter = diameter;
    }

    public Coin(Double mass, Double diameter, Double value) {
        this.mass = mass;
        this.diameter = diameter;
        this.value = value;
    }

    public Double getMass() {
        return mass;
    }

    public void setMass(Double mass) {
        this.mass = mass;
    }

    public Double getDiameter() {
        return diameter;
    }

    public void setDiameter(Double diameter) {
        this.diameter = diameter;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
