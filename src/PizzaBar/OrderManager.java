package PizzaBar;

import java.util.ArrayList;

public class OrderManager {

    private final ArrayList<Order> orders = new ArrayList<>();

    public Order createOrder(String customerName, String phone){
        //TODO: create new Order object based on user input, and add to list
        Order o = new Order(customerName, phone);
        orders.add(o);
        return o;
    }

    public void getActiveOrders(){
        //TODO: sort the list of active orders by time
        //TODO: loop through and print the list of active orders
    }

    public Order findOrderById(int id){
        //TODO: select an order Id
        // loop through the list of orders to find selected order
        for(Order o : orders){
            if(o.getId() == id){
                return o;
            }
        }
        return null;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void deleteOrder(int id){
        //TODO: select an order by Id
        //TODO: remove the selected order from the list
        // use a try catch to validate if the selected order exists in the list
        try{
            Order o = findOrderById(id);
            orders.remove(o);
        } catch (IllegalArgumentException e){
            System.out.println("No order found at id: " + id);
        }
    }

    public void modifyOrder(){
        //TODO: select an order based on Id
        // use a try catch to validate if the selected order exists in the list
        //TODO: select an attribute to modify
        //TODO: modify the order attribute
    }

    public void printOrders(){
        for(Order o : orders){
            System.out.println(o);
            System.out.println("---------------------");
        }
    }
}
