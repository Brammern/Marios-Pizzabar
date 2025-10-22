package PizzaBar.app;

import PizzaBar.logic.*;
import PizzaBar.helpers.*;
import PizzaBar.products.*;

public class Application {
    // TODO: Implement the application flow here
    // TODO: call methods to run app
    private final OrderManager manager = new OrderManager();
    private final OrderHelper helper = new OrderHelper(manager);
    FileHandler fh = new FileHandler();
    public void run(){
        boolean run = true;
        FileHandler.createFile(fh.getSoldFile());
        FileHandler.createFile(fh.getAllOrdersFile());
        System.out.println();
        while(run) {
            System.out.println("\n=== ORDER MENU ===");
            System.out.println("1. Show menu");
            System.out.println("2. Create new order");
            System.out.println("3. Show all orders");
            System.out.println("4. Change status of an order");
            System.out.println("5. Close program");
            System.out.print("Choose: ");

            int choice = helper.readInt("");
            System.out.println();

            switch (choice){
                case 1 -> Pizza.printMenu();
                case 2 -> helper.createOrder();
                case 3 -> manager.printOrders();
                case 4 -> helper.changeStatus();
                case 5 -> {
                    System.out.println("Goodbye!");
                    run = false;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
