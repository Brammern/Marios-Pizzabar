package PizzaBar.logic;

import PizzaBar.products.Order;
import java.util.ArrayList;
import java.util.Collections;


public class OrderManager {
    //ArrayList til at holde på alle vores ordrer.
    private final ArrayList<Order> orders = new ArrayList<>();

    /**
     * Laver en ny ordre og tilføjer den til vores ArrayList
     * @param customerName Kundens navn
     * @param phone Kundens tlf.
     * @return Order o
     */
    public Order createOrder(String customerName, String phone){
        Order o = new Order(customerName, phone);
        orders.add(o);
        return o;
    }

    /**
     * Viser en oversigt over alle aktive ordre.
     * Alle ordrer med status READY_FOR_PICKUP eller ACTIVE
     */
    public void getActiveOrders(){
        System.out.println("=== ACTIVE ORDERS ===");
        for (Order o : orders){
            if(o.getStatus().equals(Order.Status.ACTIVE) || o.getStatus().equals(Order.Status.READY_FOR_PICKUP)){
                System.out.println(o);
                System.out.println("--------------------");
            } else {
                System.out.println("No active orders at the moment.");
            }
        }
    }

    /**
     * Bruges til at finde en specifik ordre ud fra et ordre id
     * @param id Skal bruge et ordre id som input
     * @return Order eller null hvis ordren ikke findes
     */
    public Order findOrderById(int id){
        for(Order o : orders){
            if(o.getId() == id){
                return o;
            }
        }
        return null;
    }

    /**
     * Sletter en ordre fra vores ArrayList
     * @param id Skal bruge et ordre id til at finde en ordre
     */
    public void deleteOrder(int id){
        try{
            Order o = findOrderById(id);
            orders.remove(o);
        } catch (IllegalArgumentException e){
            System.out.println("No order found at id: " + id);
        }
    }

    /**
     * Udskriver alle ordre uanset deres status
     */
    public void printOrders(){
        System.out.println("\n=== ALL ORDERS ===");
        sortByTime();
        for(Order o : orders){
            System.out.println(o);
            System.out.println("---------------------");
        }
    }

    /**
     * Sorterer ordrer i ArrayListen efter afhentningstidspunkt
     */
    public void sortByTime(){
        Collections.sort(orders, new CompareByTime());
    }

}
