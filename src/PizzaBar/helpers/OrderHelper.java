package PizzaBar.helpers;

import PizzaBar.app.Application;
import PizzaBar.products.Order;
import PizzaBar.logic.*;
import PizzaBar.products.Pizza;
import PizzaBar.products.Size;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class OrderHelper {
    private final Scanner scanner = new Scanner(System.in);
    private final OrderManager manager;

    public OrderHelper(OrderManager manager){
        this.manager = manager;
    }

    public void createOrder(){
        String name = readString("Type in the customers name (leave blank for walk-in): ");
        String phone = readPhone("Type in the customers phone number (leave blank for walk-in): ");

        System.out.println("Add pizzas (type 0 to cancel order):");

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

            if(amount <= 0){
                System.out.println("Amount of pizzas can't be 0. Try again");
                continue;
            }
            Order order = manager.createOrder(name, phone);
            boolean realityCheck = realityCheck("Do you want to add more pizzas to your order?");
            if (realityCheck) {
                order.addLine(pizza, size, amount);
            } else {
                order.addLine(pizza, size, amount);
                int minutes = readInt("When do you wish to pickup your order? (in minutes): ");
                order.setPickupTimeInMinutes(minutes);
                System.out.println("\nOrder #" + order.getId() + " has been crated successfully." +
                        "\nYour order will be ready at: " + order.getPickupTime().format(Order.timeFormatter));
                break;
            }
        }
    }

    // Changes the status of the order depending on the choice of the switch.
    public void changeStatus(){
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
            System.out.println("4. DELETE order");
            System.out.println("5. Go back");
            System.out.print("Choice: ");
            int choice = readInt("");
            System.out.println();

            switch (choice){
                case 1 -> markAsReady(id);
                case 2 -> markAsFinished(id);
                case 3 -> cancelOrder(id);
                case 4 -> deleteOrder(id);
                case 5 -> running = false;
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

    private void deleteOrder(int id){
        final Application app = new Application();
        boolean realityCheck = realityCheck("Are you sure you want to delete the order: #" + id + "?");
        if (realityCheck) {
            Order o = manager.findOrderById(id);
            manager.deleteOrder(id);
            System.out.println("Order #" + id + " has been deleted successfully!");
            app.run();
        }
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

    public String readString (String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int readInt (String prompt) {
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

    public boolean realityCheck(String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n)");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")) {
                return true;
            } else if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no")) {
                return false;
            } else {
                System.out.println("Invalid answer. Please only answer with yes or no");
            }
        }
    }
}
