package PizzaBar;

public enum Size {
    STANDARD(1.0),
    FAMILY(1.25);

    private final double priceFactor;
    Size(double priceFactor){
        this.priceFactor=priceFactor;
    }

    public double getPriceFactor() {
        return priceFactor;
    }
}
