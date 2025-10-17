package PizzaBar.products;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    //TODO: make order attributes
    private int id;
    private String customerName;
    private LocalDateTime pickupTime;
    private String phone;
    private String status;
    private List<Pizza> pizzas = new ArrayList<>();


    // TODO: make getters and setters for attributes

    //Getters

    public int getId() {return id;}
    public String getCustomerName() {return customerName;}
    public LocalDateTime getPickupTime() {return pickupTime;}
    public String getPhone() {return phone;}
    public String getStatus() {return status;}
    public List<Pizza> getPizzas() {return pizzas;}

    //setters
    public void setId(int id) {this.id = id;}
    public void setCustomerName(String customerName) {this.customerName = customerName;}
    public void setPickupTime(LocalDateTime pickupTime) {this.pickupTime = pickupTime;}
    public void setPhone(String phone) {this.phone = phone;}
    public void setStatus(String status) {this.status = status;}
    public void setPizzas(List<Pizza> pizzas) {this.pizzas = pizzas;}

    //TODO: make addPizza() method, adds a pizza to the list of orders.
    //TODO: make getTotalPrice() method
    //TODO: make setStatus method
}
