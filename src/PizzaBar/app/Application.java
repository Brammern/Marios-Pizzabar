package PizzaBar.app;

import PizzaBar.logic.*;
import PizzaBar.helpers.*;
import PizzaBar.products.*;

import java.util.Scanner;

public class Application {
    private final OrderManager manager = new OrderManager();
    private final OrderHelper helper = new OrderHelper(manager);

    /**
     * Metode til at køre vores applikation. Kalder relevante metoder ved hjælp af en switch.
     */
    public void run(){
        boolean run = true;
        System.out.println();
        while(run) {
            System.out.println("\n=== ORDER MENU ===");
            System.out.println("1. Show menu");
            System.out.println("2. Create new order");
            System.out.println("3. Show active orders");
            System.out.println("4. Show all orders");
            System.out.println("5. Change status of an order");
            System.out.println("6. Close program");
            System.out.print("Choose: ");

            int choice = helper.readInt("");
            System.out.println();

            switch (choice){
                case 1 -> Pizza.printMenu();
                case 2 -> helper.createOrder();
                case 3 -> manager.getActiveOrders();
                case 4 -> manager.printOrders();
                case 5 -> helper.changeStatus();
                case 6 -> {
                    System.out.println("Goodbye!");
                    run = false;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
