package PizzaBar;

import java.util.Scanner;

public class OrderHelper {
    //TODO: import scanner, and make a scanner for user input
    private final Scanner scanner = new Scanner(System.in);
    private final OrderManager helper;

    OrderHelper(OrderManager helper){
        this.helper = helper;
    }

    public void run(){
        boolean run = true;
        while(run) {
            System.out.println("\n=== ORDER MENU ===");
            System.out.println("1. Create new order");
            System.out.println("2. Show all orders");
            System.out.println("3. Mark order as ready");
            System.out.println("4. Close program");
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
                case 3 -> markAsReady();
                case 4 -> {
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
        Order order = helper.createOrder(name, phone);

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

    }

    private void showAllOrders(){
        System.out.println("\n=== ALL ORDERS ===");
        for (Order o : helper.getOrders()){
            System.out.println(o);
            System.out.println("----------------------");
        }
    }

    private void markAsReady(){
        System.out.print("Input order-id: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Order o = helper.findOrderById(id);
        if(o == null){
            System.out.println("No order with id " + id);
            return;
        }

        o.finish();
        System.out.println("Order #" + id + " marked as ready for pickup :-)");
    }
}
