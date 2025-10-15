package PizzaBar;

import javax.print.DocFlavor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Order {
    //TODO: make order attributes

    public enum Status {ACTIVE, FINISHED, CANCELLED}

    private int id;
    private static int nextId = 1;
    private String customerName;
    private LocalDateTime pickupTime;
    private String phone;
    private Status status;
    private final List<OrderLine> lines = new ArrayList<>();
    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public Order(String customerName, String phone){
        this.id = nextId;
        nextId++;
        this.customerName = (customerName == null || customerName.isBlank()) ? "Walk-in" : customerName.trim();
        this.phone = phone;
        this.pickupTime = LocalDateTime.now();
        this.status = Status.ACTIVE;
    }
    // TODO: make getters and setters for attributes

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


    public void addLine(Pizza pizza, Size size, int amount){
        if(amount <= 0)
            throw new IllegalArgumentException("Amount has to be > 0");
        double price = pizza.getPrice(size);
        lines.add(new OrderLine(pizza, size, price, amount));
    }

    public void finish(){
        if(lines.isEmpty()) throw new IllegalArgumentException("Cannot finish empty order");
        status = Status.FINISHED;
    }

    public void cancel(){
        this.status = Status.CANCELLED;
    }

    public double total(){
        double sum = 0;
        for(OrderLine l : lines){
            sum += l.lineTotal();
        }
        return sum;
    }

    public boolean hasPickupTime(){return pickupTime != null;}

    public long minutesToPickup(){
        if(!hasPickupTime()) return -1;
        return java.time.Duration.between(LocalDateTime.now(), pickupTime).toMinutes();
    }


    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Ordre #").append(id).append(" (").append(customerName).append(")\n");
        for (OrderLine l : lines) sb.append("  - ").append(l).append("\n");
        sb.append(String.format("Total: %.2f kr%n", total()));

        if (hasPickupTime()) {
            sb.append("Afhentning: ").append(timeFormatter.format(pickupTime));
            long min = minutesToPickup();
            if (min >= 0) sb.append(" (om ").append(min).append(" min)");
            sb.append("\n");
        } else {
            sb.append("Afhentning: ikke sat\n");
        }

        sb.append("Status: ").append(status);
        return sb.toString();
    }
}

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
