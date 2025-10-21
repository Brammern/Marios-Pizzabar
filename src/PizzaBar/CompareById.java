package PizzaBar;

import PizzaBar.products.Order;

import java.util.Comparator;
//Comparator is an interface and has to be in a class of its own.
public class CompareById implements Comparator<Order> {

    @Override
    public int compare(Order o1, Order o2) {
        return Integer.compare(o1.getId(), o2.getId());
    }
}
