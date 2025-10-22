package PizzaBar.helpers;

import PizzaBar.app.Application;
import PizzaBar.products.Order;
import PizzaBar.logic.*;
import PizzaBar.products.Pizza;
import PizzaBar.products.Size;
import java.io.IOException;
import java.util.Scanner;


public class OrderHelper {
    private final Scanner scanner = new Scanner(System.in);
    private final OrderManager manager;
    private final FileHandler fh = new FileHandler();
    //Constructor
    public OrderHelper(OrderManager manager){
        this.manager = manager;
    }

    /**
     * Metode til at lave en ordre ud fra bruger-input.
     * Kalder de relevante metoder der skal bruges for at lave en ordre.
     * Sørger for at validere brugerinput så programmet ikke crasher
     */
    public void createOrder(){
        String name = readString("Type in the customers name (leave blank for walk-in): ");
        String phone = readPhone("Type in the customers phone number (leave blank for walk-in): ");

        System.out.println("Add pizzas (type 0 to cancel order):");
        Order o = null;
        while(true){
            String pizzaName = readString("Pizza name: ");
            if(pizzaName.equals("0")) break;


            Pizza pizza = Pizza.findByName(pizzaName);
            if(pizza == null){
                System.out.println("Pizza not found in menu.");
                continue;
            }

            System.out.print("Choose size (1=STANDARD, 2=FAMILYSIZE): ");
            int sizeChoice;
            while (true) {
                sizeChoice = readInt("");
                if (sizeChoice == 1 || sizeChoice == 2) break;
                System.out.print("Invalid size choice. Try again: ");
            }

            Size size = (sizeChoice == 2) ? Size.FAMILY : Size.STANDARD;

            System.out.print("Enter the desired amount of " + pizzaName + ": ");
            int amount = readInt("");

            if(amount <= 0){
                System.out.println("Amount of pizzas can't be 0. Try again");
                continue;
            }

            if (o == null) {
                o = manager.createOrder(name, phone);
            }
            o.addLine(pizza, size, amount);

            boolean morePizzas = realityCheck("Do you want to add more pizzas to your order?");
            if (!morePizzas) {
                break;
            }
        }

        int minutes = readInt("Enter pickup time in minutes from now (0 for no pickup time): ");
        while (minutes < 15) {
            minutes = readInt("Pickup time must be at least 15 minutes. Please enter a valid pickup time in minutes: ");
        }
        o.setPickupTimeInMinutes(minutes);

        System.out.println("\nOrder #" + o.getId() + " has been crated successfully." +
                "\nYour order will be ready at: " + o.getPickupTime().format(o.timeFormatter));
    }

    // Ændrer status på en ordre ud fra et valg og en switch
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

    /**
     * Sætter ordrestatus på en ordre til READY_FOR_PICKUP
     * @param id Skal bruge et id for en ordre
     */
    private void markAsReady(int id){
        Order o = manager.findOrderById(id);
        if(o == null){
            System.out.println("No order with id " + id);
            return;
        }

        o.readyForPickup();
        System.out.println("Order #" + id + " marked as ready for pickup :-)");
    }

    /**
     * Sætter ordrestatus på en ordre til FINISHED og udskriver den til en CSV fil
     * @param id Skal bruge et id for en ordre
     */
    private void markAsFinished(int id){
        Order o = manager.findOrderById(id);
        if(o == null){
            System.out.println("No order with id " + id);
            return;
        }

        o.finish();
        try {
            fh.appendOrderFinished(o);
            fh.appendOrderCreated(o);
        } catch (IOException e) {
            System.out.println("⚠️ CSV write error: " + e.getMessage());
        }
        //fh.writeToFile(fh.getSoldFile(), o.toString());
        //fh.writeToFile(fh.getAllOrdersFile(), o.toString());
        System.out.println("Order #" + id + " marked as finished :-)");
    }

    /**
     * Sletter en ordre og fjerner den fra ordrelisten
     * @param id Skal bruge et id for en ordre
     */
    private void deleteOrder(int id){
        final Application app = new Application();
        boolean confirmDelete = realityCheck("Are you sure you want to delete the order: #" + id + "?");
        if (confirmDelete) {
            Order o = manager.findOrderById(id);
            manager.deleteOrder(id);
            System.out.println("Order #" + id + " has been deleted successfully!");
            app.run();
        }
    }

    /**
     * Sætter ordrestatus på en ordre til CANCELLED.
     * Skriver den opdaterede ordre til en CSV fil
     * @param id Skal bruge et id for en ordre
     */
    private void cancelOrder(int id){
        Order o = manager.findOrderById(id);
        if(o == null){
            System.out.println("No order with id " + id);
            return;
        }
        o.cancel();
        try {
            fh.appendOrderCreated(o);
        } catch (IOException e) {
            System.out.println("⚠️ CSV write error: " + e.getMessage());
        }
        //fh.writeToFile(fh.getAllOrdersFile(), o.toString());
        System.out.println("Order #" + id + " has been cancelled.");
    }

    //=== VALIDATION METODER ===

    /**
     * Læser en string fra brugeren
     * @param prompt Den tekst som man skal bruge til at spørge brugeren om noget data.
     * @return Returnerer scanner.nextLine()
     */
    public String readString (String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    /**
     * Læser en integer og validerer om det er et gyldigt input fra brugeren
     * @param prompt Den tekst der spørger brugeren om en int værdi
     * @return Integer.parseInt(scanner.nextInt())
     */
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

    /**
     * Bruges til at læse inputtet til tlf nr. fra brugeren.
     * Validerer om det er en String på 8 tal
     * @param prompt Spørger brugeren om telefonnummer på kunden
     * @return input eller WALK-IN
     */
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

    /**
     * En ekstra validering der bruges til at sikre at brugeren ikke kommer til at gøre noget,
     * de ikke ville have gjort
     * @param prompt Spørger brugeren en ekstra gang om de er sikre på at de vil foretage sig en bestemt handling
     * @return true/false
     */
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
