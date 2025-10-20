package PizzaBar.logic;
// import anything necessary
import PizzaBar.logic.*;
import PizzaBar.products.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileHandler {
    //TODO: variable fileName, should be added in the class these methods are called to
    //TODO: create a FileHandler object in the logic class to run these methods.

        //method for creating new CSV file
    //parameter when calling this method should have the format: "SoldPizzas.csv"
    public void createFile(String filename){
        //try catch to validate creating a new csv file
        try {
            File file = new File(filename); // Fil-objekt

            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
                System.out.println("Absolute path: " + file.getAbsolutePath());
            } else {
                System.out.println("File already exists at: " + file.getAbsolutePath());
            }

        } catch (IOException e) {
            System.out.println("An error occurred while creating the file.");
            e.printStackTrace();
        }
    }
        //method that writes to the file
    //use fileName variable as parameter when calling method
    public void writeFile(String filename) {
        //TODO: make a statement to add sold pizza orders to the file
        try {
            // Create a FileWriter (will create file if it does not exist)
            FileWriter writer = new FileWriter(filename, true);
            // Append = true means new text will be added to the end of the file.
            writer.write("\n");

            // Always close the writer to save changes
            writer.close();

            System.out.println("The pizza order has been added to the csv file.");

        } catch (IOException e) {
            System.out.println("⚠️ An error occurred: " + e.getMessage());
        }
    }
        //method for reading the file in the console
    //use the fileName variable as parameter when calling this method
    public String readFileAsString(String filePath){
        StringBuilder fileContent = new StringBuilder();

        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            // Reads the file line for line
            while (scanner.hasNextLine()) {
                fileContent.append(scanner.nextLine()).append("\n");
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("⚠️ File not found: " + e.getMessage());
        }
        return fileContent.toString();
    }
}
