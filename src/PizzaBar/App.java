package PizzaBar;

public class App {
    public static void main(String[] args) {
        //TODO: call methods to run app

        Pizza.printMenu();
        System.out.println();

        OrderManager manager = new OrderManager();
        OrderHelper oh = new OrderHelper(manager);

        oh.run();
    }

}
