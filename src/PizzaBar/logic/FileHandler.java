package PizzaBar.logic;

import PizzaBar.products.*;
import java.io.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.stream.Collectors;

public class FileHandler {
    /** Separator der bruges i CSV filer */
    private static final String SEPERATOR = ",";

    /**
     * Appender en nyoprettet ordre til filen med alle ordrer
     * @param o Ordren der skal appendes
     */
    public void appendOrderCreated(Order o) throws IOException {
        Path path = Path.of(getAllOrdersFile());
        boolean writeHeader = ensureFileAndCheckEmpty(path);

        try (var w = new PrintWriter(new OutputStreamWriter(Files.newOutputStream(path, StandardOpenOption.APPEND), StandardCharsets.UTF_8))) {
            if (writeHeader) {
                w.println(csvLine("id","customer","phone","pizzas","pickupTime","status","total"));
            }

            w.println(csvLine(
                    String.valueOf(o.getId()),
                    nz(o.getCustomerName()),
                    nz(o.getPhone()),
                    pizzasField(o),
                    o.getPickupTime().format(o.timeFormatter),
                    o.getStatus().name(),
                    moneyFormat(o.total()) + " DKK"
            ));
        }
    }

    /**
     * Appender en færdig ordre til filen med solgte pizzaer
     * @param o Ordren der skal appendes
     */
    public void appendOrderFinished(Order o) throws IOException {
        if (o.getStatus() != Order.Status.FINISHED) {
            return;
        }
        Path path = Path.of(getSoldFile());
        boolean writeHeader = ensureFileAndCheckEmpty(path);

        try (var w = new PrintWriter(new OutputStreamWriter(Files.newOutputStream(path, StandardOpenOption.APPEND), StandardCharsets.UTF_8))) {
            if (writeHeader) {
                w.println(csvLine("id","customer","phone","pizzas","pickupTime","status","total"));
            }

            w.println(csvLine(
                    String.valueOf(o.getId()),
                    nz(o.getCustomerName()),
                    nz(o.getPhone()),
                    pizzasField(o),
                    o.getPickupTime().format(o.timeFormatter),
                    o.getStatus().name(),
                    moneyFormat(o.total()) + " DKK"
            ));
        }
    }

    /**
     * Sikrer at filen eksisterer
     * @param path Sti til filen
     * @return true hvis filen er tom, ellers false
     */
    private boolean ensureFileAndCheckEmpty(Path path) throws IOException {
        if (path.getParent() !=null) {
            Files.createDirectories(path.getParent());
            System.out.println("FileHandler: Created directories for path '" + path.getParent() + "'");
        }
        if (!Files.exists(path)) {
            Files.createFile(path);
            System.out.println("FileHandler: Created file '" + path + "'");
            return true;
        }
        return Files.size(path) == 0;
    }

    /**
     * Laver en CSV linje ud fra et array af felter
     * @param fields Felter der skal med i linjen
     * @return CSV linje som String
     */
    private String csvLine(String... fields) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            if (i > 0) sb.append(SEPERATOR);
            sb.append(csvEscape(fields[i]));
        }
        return sb.toString();
    }

    /**
     * Escape et felt til CSV format
     * @param s Feltet der skal escapes
     * @return Escaped felt som String
     */
    private String csvEscape(String s) {
        if (s == null) return "";
        boolean mustQuote = s.contains(SEPERATOR) || s.indexOf('"') >= 0 || s.indexOf('\n') >= 0;
        if (!mustQuote) return s;
        String escaped = s.replace("\"", "\"\"");
        return "\"" + escaped + "\"";
    }

    /**
     * Laver en String der repræsenterer pizza linjerne i en ordre
     * @param o Ordren der skal laves String for
     * @return Pizza linjer som String
     */
    private String pizzasField(Order o) {
        return o.getLines().stream()
                .map(l -> l.getAmount() + "x "
                        + l.getPizza()
                        + " (" + l.getSize().name() + ")")
                .collect(Collectors.joining(" | "));
    }

    /**
     * Håndterer null Strings
     * @param s String der skal håndteres
     * @return Tom String hvis s er null, ellers s
     */
    private String nz(String s) {
        return s == null ? "" : s;
    }

    /**
     * Formaterer et double til to decimaler
     * @param v Double der skal formateres
     * @return Formateret double som String
     */
    private String moneyFormat(double v) {
        return String.format("%.2f", v);
    }

    /** Getters for filnavne */
    public String getSoldFile() {
        return "AllPizzasSold.csv";
    }

    public String getAllOrdersFile() {
        return "AllPizzaOrders.csv";
    }
}
