package PizzaBar;

public class App {
    public static void main(String[] args) {
        //TODO: call methods to run app

        Pizza.printMenu();

        OrderManager manager = new OrderManager();

        Order o1 = manager.createOrder("Anders", "12345678");
        o1.addLine(Pizza.BACON, Size.FAMILY, 2);
        o1.addLine(Pizza.PEPPERONI, Size.STANDARD, 1);
        o1.finish();

        Order o2 = manager.createOrder("Brian", "69696969");
        o2.addLine(Pizza.BOLOGNESE, Size.STANDARD, 1);


        manager.printOrders();
    }

}
