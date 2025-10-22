package PizzaBar.products;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Order {
    //Enum til Order Status
    public enum Status {ACTIVE, READY_FOR_PICKUP, FINISHED,CANCELLED}

    //Attributes for order
    private int id;
    private static int nextId = 1;
    private String customerName;
    private LocalDateTime pickupTime;
    private String phone;
    private Status status;
    private final List<OrderLine> lines = new ArrayList<>();
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    //Constructor
    public Order(String customerName, String phone){
        this.id = nextId;
        nextId++; //Lægger 1 til nextId for at næste ordre for et nyt id
        this.customerName = (customerName == null || customerName.isBlank()) ? "Walk-in" : customerName.trim();
        this.phone = (phone == null || phone.isBlank()) ? "" : phone.trim();
        this.status = Status.ACTIVE;
    }

    //Getters
    public int getId() {return id;}
    public String getCustomerName() {return customerName;}
    public LocalDateTime getPickupTime() {return pickupTime;}
    public String getPhone() {return phone;}
    public Status getStatus() {return status;}
    public List<OrderLine> getLines() {return lines;}

    //Setters
    public void setId(int id) {this.id = id;}
    public void setCustomerName(String customerName) {this.customerName = customerName;}
    public void setPickupTime(LocalDateTime pickupTime) {this.pickupTime = pickupTime;}
    public void setPickupTimeInMinutes(int minutes) {this.pickupTime = LocalDateTime.now().plusMinutes(minutes);}
    public void setPhone(String phone) {this.phone = phone;}
    public void setStatus(Status status) {this.status = status;}

    /**
     * Metode til at tilføje en linje til vores ordre ved hjælp af vores indre klasse OrderLine
     * @param pizza Skal bruge en pizza til vores linje
     * @param size Skal bruge en størrelse på pizzaen
     * @param amount Skal bruge et ønsket antal af pizzaer.
     */
    public void addLine(Pizza pizza, Size size, int amount){
        if(amount <= 0)
            throw new IllegalArgumentException("Amount has to be > 0");
        double price = pizza.getPrice(size);
        lines.add(new OrderLine(pizza, size, price, amount));
    }

    /**
     * Looper igennen alle linjer i en ordre for at få en total pris
     * @return sum af alle linjer i en ordre
     */
    public double total(){
        double sum = 0;
        for(OrderLine l : lines){
            sum += l.lineTotal();
        }
        return sum;
    }

    //=== HJÆLPE METODER ===

    /**
     * Tjekker om en ordre har en afhæntningstid.
     * @return pickupTime eller null
     */
    public boolean hasPickupTime(){return pickupTime != null;}

    /**
     * Regner ud hvor mange minutter der er til afhæntning af en ordre
     * @return java.time.Duration.between(LocalDateTime.now(), pickupTime).toMinutes() eller -1
     */
    public long minutesToPickup(){
        if(!hasPickupTime()) return -1;
        return java.time.Duration.between(LocalDateTime.now(), pickupTime).toMinutes();
    }

    //=== ÆNDRE STATUS ===

    /**
     * Sætter ordrestatus til READY_FOR_PICKUP
     */
    public void readyForPickup(){
        if(lines.isEmpty()) throw new IllegalArgumentException("Cannot change empty order");
        status = Status.READY_FOR_PICKUP;
    }

    /**
     * Sætter ordrestatus til FINISHED
     */
    public void finish(){
        if(lines.isEmpty()) throw new IllegalArgumentException("Cannot finish empty order");
        status = Status.FINISHED;
    }

    /**
     * Sætter ordrestatus til CANCELLED
     */
    public void cancel(){
        this.status = Status.CANCELLED;
    }

    /**
     * Bruger en StringBuilder til at lave en pæn format til vores ordre
     * @return sb.toString
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Order #").append(id).append(" (").append(customerName).append(")").append(" tlf: ").append(phone).append("\n");
        for (OrderLine l : lines) sb.append("  - ").append(l).append("\n");
        sb.append(String.format("Total: %.2f kr%n", total()));

        if (hasPickupTime()) {
            sb.append("Pickup: ").append(timeFormatter.format(pickupTime));
            long min = minutesToPickup();
            if (min >= 0) sb.append(" (in ").append(min).append(" min)");
            sb.append("\n");
        } else {
            sb.append("Pickup: not set\n");
        }

        sb.append("Status: ").append(status).append("\n");
        return sb.toString();
    }
}

/**
 * Indre klasse til linjer i en ordre
 */
final class OrderLine{
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
        return amount + "x " + pizza.getName() + " á " + price + " kr = " + lineTotal() + " kr";
    }
}
