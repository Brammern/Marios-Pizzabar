package PizzaBar;

import PizzaBar.products.Order;
import java.util.Comparator;

//use Comparator interface to compare orders by pickup time.
public class CompareByTime implements Comparator<Order> {

    @Override
    public int compare(Order o1, Order o2){
        return Long.compare(o2.minutesToPickup(), o1.minutesToPickup());
    }
}
