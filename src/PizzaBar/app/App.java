package PizzaBar.app;

import PizzaBar.helpers.OrderHelper;
import PizzaBar.logic.OrderManager;

public class App {
    public static void main(String[] args) {
        //TODO: call methods to run app

        OrderManager manager = new OrderManager();
        OrderHelper oh = new OrderHelper(manager);

        oh.run();
    }

}
