package PizzaBar;

import java.util.Scanner;

public class OrderHelper {
    //TODO: import scanner, and make a scanner for user input
    private final Scanner scanner = new Scanner(System.in);
    private final OrderManager manager;

    OrderHelper(OrderManager manager){
        this.manager = manager;
    }

    public void run(){
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
            int choice = scanner.nextInt();
            scanner.nextLine();


            switch (choice){
                case 1 -> createOrder();
                case 2 -> showAllOrders();
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
        System.out.print("Type in the customers name (leave blank for walk-in): ");
        String name = scanner.nextLine();
        System.out.print("Type in the customers phone number (leave blank for walk-in): ");
        String phone = scanner.nextLine();
        Order order = manager.createOrder(name, phone);

        System.out.println("Add pizzas (type 0 to stop):");

        while(true){
            System.out.print("Pizza name: ");
            String pizzaName = scanner.nextLine();
            if(pizzaName.equals("0")) break;


            Pizza pizza = Pizza.findByName(pizzaName);
            if(pizza == null){
                System.out.println("Pizza not found in menu.");
                continue;
            }

            System.out.print("Choose size (1=STANDARD, 2=FAMILYSIZE): ");
            while(!scanner.hasNextInt()){
                System.out.println("Please enter a number (1 or 2)");
                scanner.nextLine();
                System.out.print("Try again: ");
            }
            int sizeChoice = scanner.nextInt();
            scanner.nextLine();
            Size size = (sizeChoice == 2) ? Size.FAMILY : Size.STANDARD;

            System.out.print("Enter the desired amount of " + pizzaName + ": ");
            int amount = scanner.nextInt();
            scanner.nextLine();

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

    private void changeStatus(){
        System.out.print("Input order-id: ");
        int id = scanner.nextInt();
        scanner.nextLine();

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
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice){
                case 1 -> markAsReady(id);
                case 2 -> markAsFinished(id);
                case 3 -> {
                    return;
                }
                case 4 -> running = false;
                default -> System.out.println("Please enter a valid number");
            }

        }
    }

    private void showAllOrders(){
        System.out.println("\n=== ALL ORDERS ===");
        for (Order o : manager.getOrders()){
            System.out.println(o);
            System.out.println("----------------------");
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

    private void markAsFinished(int id){
        Order o = manager.findOrderById(id);
        if(o == null){
            System.out.println("No order with id " + id);
            return;
        }

        o.finish();
        System.out.println("Order #" + id + " marked as finished :-)");
    }

    private void deleteOrder(){
        System.out.println("Which order would you like to delete? (id)");
        System.out.print("Choice: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        manager.deleteOrder(id);
    }
}
