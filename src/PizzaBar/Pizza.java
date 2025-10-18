package PizzaBar;

public enum Pizza {
    MARGHERITA("Margherita", 65),
    VESUVIO("Vesuvio", 70),
    CAPRICCIOSA("Capricciosa", 75),
    HAWAII("Hawaii", 75),
    PEPPERONI("Pepperoni", 80),
    QUATTRO_FORMAGGI("Quattro Formaggi", 85),
    MARINARA("Marinara", 60),
    DIAVOLA("Diavola", 80),
    CALZONE("Calzone", 85),
    VEGETARIANA("Vegetariana", 75),
    TONNO("Tonno", 80),
    CARBONARA("Carbonara", 90),
    ROMANA("Romana", 85),
    PARMA("Parma", 95),
    BOLOGNESE("Bolognese", 85),
    MEXICANA("Mexicana", 90),
    POLLO("Pollo", 85),
    BACON("Bacon", 80),
    PICANTE("Picante", 90),
    FRUTTI_DI_MARE("Frutti di Mare", 95),
    GORGONZOLA("Gorgonzola", 85),
    PROSCIUTTO("Prosciutto", 75),
    SICILIANA("Siciliana", 80),
    QUATTRO_STAGIONI("Quattro Stagioni", 90),
    FANTASIA("Fantasia", 85),
    TROPICALE("Tropicale", 88),
    INFERNO("Inferno", 92),
    NAPOLI("Napoli", 80),
    SALAME("Salame", 78),
    FORMAGGIO_BIANCO("Formaggio Bianco", 87);

    private final String name;
    private final double basePrice;
    Pizza(String name, double basePrice){
        this.name = name;
        this.basePrice = basePrice;
    }

    //Getters

    public String getName() {
        return name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public double getPrice(Size size){
        return basePrice * size.getPriceFactor();
    }

    public static void printMenu(){
        System.out.println("=================================== Pizza Menukort ===================================\n");
        Pizza[] pizzas = Pizza.values();

        for (int i = 0; i < pizzas.length; i += 2){
            Pizza p1 = pizzas[i];
            Pizza p2 = (i + 1 < pizzas.length) ? pizzas[i + 1] : null;

            String col1 = String.format("%2d %-18s %6.2f / %6.2f kr",
                    i + 1,
                    p1.name,
                    p1.getPrice(Size.STANDARD),
                    p1.getPrice(Size.FAMILY));

            String col2 = "";
            if(p2 != null){
                col2 = String.format("      %2d %-18s %6.2f / %6.2f kr",
                        i + 2,
                        p2.name,
                        p2.getPrice(Size.STANDARD),
                        p2.getPrice(Size.FAMILY));
            }
            System.out.println(col1 + col2);
        }
    }

    public static Pizza findByName(String name) {
        for (Pizza p : values()) {
            if (p.getName().equalsIgnoreCase((name))) return p;
        }
        return null;
    }


    @Override
    public String toString(){
        return name;
    }
}
