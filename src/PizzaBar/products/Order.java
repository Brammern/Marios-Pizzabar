package PizzaBar.products;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Order {
    //TODO: make order attributes

    //Enum for order status
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
        nextId++;
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

    //setters
    public void setId(int id) {this.id = id;}
    public void setCustomerName(String customerName) {this.customerName = customerName;}
    public void setPickupTime(LocalDateTime pickupTime) {this.pickupTime = pickupTime;}
    public void setPickupTimeInMinutes(int minutes) {this.pickupTime = LocalDateTime.now().plusMinutes(minutes);}
    public void setPhone(String phone) {this.phone = phone;}
    public void setStatus(Status status) {this.status = status;}

    //Method for adding lines to order
    public void addLine(Pizza pizza, Size size, int amount){
        if(amount <= 0)
            throw new IllegalArgumentException("Amount has to be > 0");
        double price = pizza.getPrice(size);
        lines.add(new OrderLine(pizza, size, price, amount));
    }

    //Find the total cost of order
    public double total(){
        double sum = 0;
        for(OrderLine l : lines){
            sum += l.lineTotal();
        }
        return sum;
    }

    //---Helper methods---
    public boolean hasPickupTime(){return pickupTime != null;}

    public long minutesToPickup(){
        if(!hasPickupTime()) return -1;
        return java.time.Duration.between(LocalDateTime.now(), pickupTime).toMinutes();
    }

    //---Change of Status---
    public void readyForPickup(){
        if(lines.isEmpty()) throw new IllegalArgumentException("Cannot change empty order");
        status = Status.READY_FOR_PICKUP;
    }


    //Finish the order
    public void finish(){
        if(lines.isEmpty()) throw new IllegalArgumentException("Cannot finish empty order");
        status = Status.FINISHED;
    }

    //Cancel the order
    public void cancel(){
        this.status = Status.CANCELLED;
    }

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

        sb.append("Status: ").append(status);
        return sb.toString();
    }
}

//inner class for orderlines
final class OrderLine{
    private final Pizza pizza;
    private final Size size;
    private final double price;
    private final int amount;

    public OrderLine(Pizza pizza, Size size, double price,int amount){
        this.pizza = pizza;
        this.size = size;
        this.price = price;
        this.amount = amount;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public Size getSize() {
        return size;
    }

    public int getAmount() {
        return amount;
    }

    public double lineTotal(){
        return amount * pizza.getPrice(size);
    }

    @Override
    public String toString(){
        return amount + "x " + pizza.getName() + " á " + price + " kr = " + lineTotal() + " kr";
    }
}
