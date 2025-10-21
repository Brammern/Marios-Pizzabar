package PizzaBar.helpers;

import PizzaBar.products.Order;
import PizzaBar.logic.*;
import PizzaBar.products.Pizza;
import PizzaBar.products.Size;

import java.util.Scanner;

public class OrderHelper {
    private final Scanner scanner = new Scanner(System.in);
    private final OrderManager manager;

    public OrderHelper(OrderManager manager){
        this.manager = manager;
    }

    public void run(){
        FileHandler.createFile(fh.getFileName());
        boolean run = true;
        Pizza.printMenu();
        System.out.println();
        while(run) {
            System.out.println("\n=== ORDER MENU ===");
            System.out.println("1. Create new order");
            System.out.println("2. Show all orders");
            System.out.println("3. Change status of orders");
            System.out.println("4. Delete order");
            System.out.println("5. Close program");
            System.out.print("Choose: ");

            if(!scanner.hasNextInt()){
                System.out.println("Please enter a number.");
                scanner.nextLine();
                continue;
            }
            int choice = readInt("");
            System.out.println();

            switch (choice){
                case 1 -> createOrder();
                case 2 -> manager.printOrders();
                case 3 -> changeStatus();
                case 4 -> deleteOrder();
                case 5 -> {
                    System.out.println("Goodbye!");
                    run = false;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private void createOrder(){
        String name = readString("Type in the customers name (leave blank for walk-in): ");
        String phone = readPhone("Type in the customers phone number (leave blank for walk-in): ");

        Order order = manager.createOrder(name, phone);

        System.out.println("Add pizzas (type 0 to stop):");

        while(true){
            String pizzaName = readString("Pizza name: ");
            if(pizzaName.equals("0")) break;


            Pizza pizza = Pizza.findByName(pizzaName);
            if(pizza == null){
                System.out.println("Pizza not found in menu.");
                continue;
            }

            System.out.print("Choose size (1=STANDARD, 2=FAMILYSIZE): ");
            int sizeChoice = 0;
            boolean valid = false;
            while (!valid) {
                sizeChoice = readInt("");
                if (sizeChoice == 1 || sizeChoice == 2) {
                    valid = true;
                } else {
                    System.out.print("Invalid size choice. Try again: ");
                }
            }
            Size size = (sizeChoice == 2) ? Size.FAMILY : Size.STANDARD;

            System.out.print("Enter the desired amount of " + pizzaName + ": ");
            int amount = readInt("");
            //scanner.nextLine();

            if(amount <= 0){
                System.out.println("Amount of pizzas can't be 0. Try again");
                continue;
            }

            order.addLine(pizza, size, amount);
        }

        System.out.print("When does the order have to get picked up (in minutes): ");
        int minutes = scanner.nextInt();
        scanner.nextLine();

        order.setPickupTimeInMinutes(minutes);

    }

    // Changes the status of the order depending on the choice of the switch.
    private void changeStatus(){
        int id = readInt("Input order-id: ");

        Order o = manager.findOrderById(id);
        if(o == null){
            System.out.println("No order with id " + id);
            return;
        }
        boolean running = true;
        while(running) {
            System.out.println("==========================");
            System.out.println("Choose one of the following options:");
            System.out.println("1. Mark order as ready for pickup");
            System.out.println("2. Mark order as finished");
            System.out.println("3. Cancel order");
            System.out.println("4. Go back");
            System.out.print("Choice: ");
            int choice = readInt("");
            System.out.println();

            switch (choice){
                case 1 -> markAsReady(id);
                case 2 -> markAsFinished(id);
                case 3 -> cancelOrder(id);
                case 4 -> running = false;
                default -> System.out.println("Please enter a valid number");
            }
        }
    }

    private void markAsReady(int id){
        Order o = manager.findOrderById(id);
        if(o == null){
            System.out.println("No order with id " + id);
            return;
        }

        o.readyForPickup();
        System.out.println("Order #" + id + " marked as ready for pickup :-)");
    }

    FileHandler fh = new FileHandler();
    private void markAsFinished(int id){
        Order o = manager.findOrderById(id);
        if(o == null){
            System.out.println("No order with id " + id);
            return;
        }

        o.finish();
        fh.writeToFile(fh.getFileName(), o.toString());
        System.out.println("Order #" + id + " marked as finished :-)");
    }

    private void deleteOrder(){
        int id = readInt("Choose the order ID: ");
        manager.deleteOrder(id);
        System.out.println("Order #" + id + " has been deleted successfully!");
    }

    private void cancelOrder(int id){
        Order o = manager.findOrderById(id);
        if(o == null){
            System.out.println("No order with id " + id);
            return;
        }

        o.cancel();
        System.out.println("Order #" + id + " has been cancelled.");
    }

    // Validation methods

    private String readString (String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private int readInt (String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            }
            catch (NumberFormatException e) {
                System.out.print("Invalid input! Please enter a valid number. ");
                System.out.println();
            }
        }
    }

    private String readPhone (String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input.matches("\\d{8}")) {
                return input;
            } else if (input.isEmpty()) {
                return "WALK-IN";
            } else {
                System.out.println("Invalid phone number! Please enter an 8-digit phone number.");
            }
        }
    }
}
