package PizzaBar.logic;

import PizzaBar.CompareByTime;
import PizzaBar.products.Order;

import java.util.ArrayList;
import java.util.Collections;

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

    public void printOrders(){
        System.out.println("\n=== ALL ORDERS ===");
        sortByTime();
        for(Order o : orders){
            System.out.println(o);
            System.out.println("---------------------");
        }
    }

    //sort list of orders by time
    public void sortByTime(){
        Collections.sort(orders, new CompareByTime());
    }

}
