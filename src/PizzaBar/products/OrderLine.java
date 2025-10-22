package PizzaBar.products;

/**
 * Indre klasse til linjer i en ordre
 */
public final class OrderLine{
    private final Pizza pizza;
    private final Size size;
    private final double price;
    private final int amount;

    /**
     * Constructor
     * @param pizza Skal bruge en pizza
     * @param size Skal bruge en pizza størrelse
     * @param price Skal bruge en pris på pizzaen
     * @param amount Skal bruge et ønsket antal af pizzaer.
     */
    public OrderLine(Pizza pizza, Size size, double price,int amount){
        this.pizza = pizza;
        this.size = size;
        this.price = price;
        this.amount = amount;
    }

    //Getters
    public Pizza getPizza() {return pizza;}
    public Size getSize() {return size;}
    public int getAmount() {return amount;}

    /**
     * Regner den totale pris ud for en ordre linje
     * @return amount * pizza.getPrice(size)
     */
    public double lineTotal(){
        return amount * pizza.getPrice(size);
    }

    /**
     * En pæn formattering af vores ordrelinje
     * @return amount + "x " + pizza.getName() + " á " + price + " kr = " + lineTotal() + " kr"
     */
    @Override
    public String toString(){
        return amount + "x " + pizza.getName() + " of " + price + " kr = " + lineTotal() + " kr";
    }
}
