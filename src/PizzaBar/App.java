package PizzaBar;

public class App {
    public static void main(String[] args) {
        //TODO: call methods to run app

        System.out.println(Pizza);
    }

    public static void printMenu(){
        System.out.println("=================================== Pizza Menukort ===================================\n");
        Pizza[] pizzas = Pizza.values();

        for (int i = 0; i < pizzas.length; i += 2){
            Pizza p1 = pizzas[i];
            Pizza p2 = (i + 1 < pizzas.length) ? pizzas[i + 1] : null;

            String col1 = String.format("%2d %-18s %6.2f / %6.2f kr",
                    i + 1,
                    p1.getName(),
                    p1.getPrice(Size.STANDARD),
                    p1.getPrice(Size.FAMILY));

            String col2 = "";
            if(p2 != null){
                col2 = String.format("      %2d %-18s %6.2f / %6.2f kr",
                        i + 2,
                        p2.getName(),
                        p2.getPrice(Size.STANDARD),
                        p2.getPrice(Size.FAMILY));
            }
            System.out.println(col1 + col2);
        }
    }
}
