package PizzaBar.products;

/**
 * Enum for vores størrelser på en pizza. De har en fast prisfaktor-værdi
 */
public enum Size {
    STANDARD(1.0),
    FAMILY(1.25);

    private final double priceFactor;

    /**
     * Constructor
     * @param priceFactor bruger en double priceFactor
     */
    Size(double priceFactor){
        this.priceFactor = priceFactor;
    }

    //Getter
    public double getPriceFactor() {
        return priceFactor;
    }
}
