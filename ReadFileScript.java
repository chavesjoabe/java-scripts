import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadFileScript {
  public static void main(String[] args) {

    /***************************************************
    *                  IMPORTANT                       *
    * Run this script will create a folder in the root *
    *       called /out with output.csv file           *
    ***************************************************/

    int DEFAULT_FIELD_NUMBER = 3;
    List<Product> products = new ArrayList<>();
    String sourceFilePath = "./data.csv";
    String outputFolder = "out";
    String outputFilePath = outputFolder + File.separator + "output.csv";

    // Read file content and save it in a list;
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(sourceFilePath))) {
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        String[] fields = line.split(",");
        if (fields.length != DEFAULT_FIELD_NUMBER) {
          throw new IOException("WRONG FIELD NUMBER");
        }

        Product product = new Product(
            fields[0],
            Double.parseDouble(fields[1]),
            Integer.parseInt(fields[2]));

        products.add(product);
      }
    } catch (IOException exception) {
      System.out.println("ERROR ON PROCESS FILE" + exception.getMessage());
    }

    // create output folder and file if it doens't exists
    try {
      Path outputFolderPath = Paths.get(outputFolder);
      if (!Files.exists(outputFolderPath)) {
        Files.createDirectory(outputFolderPath);
      }
    } catch (Exception e) {
      System.out.println("ERROR ON CREATE FOLDER");
      return;
    }

    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFilePath))) {
      bufferedWriter.write("DESCRIPTION,PRICE,QUANTITY,TOTAL_PRICE");
      bufferedWriter.newLine();
      for (Product product : products) {
        String content = String.format("%s,%.2f,%d,%.2f",
            product.getDescription().toLowerCase(),
            product.getPrice(),
            product.getQuantity(),
            product.getTotalPrice());

        bufferedWriter.write(content);
        bufferedWriter.newLine();
      }
    } catch (Exception e) {
      System.out.println("ERROR ON WRITE FILE");
      return;
    }

    System.out.println("CSV file created in path: " + outputFilePath);
  }
}

class Product {
  private String description;
  private double price;
  private int quantity;
  private double totalPrice;

  public Product() {
  }

  public Product(
      String description,
      double price,
      int quantity) {
    this.description = description;
    this.price = price;
    this.quantity = quantity;
    this.totalPrice = price * quantity;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public double getTotalPrice() {
    return this.totalPrice;
  }

  @Override
  public String toString() {
    return "Product{" +
        "description='" + description + '\'' +
        ", price=" + price +
        ", quantity=" + quantity +
        ", totalPrice=" + totalPrice +
        '}';
  }
}
