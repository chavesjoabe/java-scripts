import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class ListIteration {
  public static double generatePrice(int value) {
    double minRandom = 10;
    double maxRandom = 30;

    double result = (double) (Math.random() * (maxRandom - minRandom)) + minRandom;
    BigDecimal bigDecimal = new BigDecimal(result).setScale(2, RoundingMode.CEILING);

    return bigDecimal.doubleValue();
  }

  public static void main(String[] args) {
    List<Integer> ids = List.of(1, 2, 3, 4, 5, 6);
    Map<Integer, Product> productMap = new HashMap<>();

    ids.forEach(id -> {
      productMap
          .put(
              id,
              new Product(
                  id,
                  "Product_" + id,
                  generatePrice(id)));
    });

    List<Integer> toApproveIds = List.of(2, 4, 5);
    toApproveIds.forEach(approvedId -> {
      productMap.computeIfPresent(approvedId, (id, product) -> {
        product.setApproved(true);
        return product;
      });
    });

    Tax tax = new Tax((double) 20);
    List<Product> finalPriceProducts = productMap
        .values()
        .stream()
        .map((product) -> {
          product.setFinalPrice(tax.applyTaxes(product.getPrice()));
          return product;
        })
        .collect(Collectors.toList());

    finalPriceProducts.stream().forEach((product) -> {
      System.out.println(product.toString());
    });
  }
}

class Product {
  private int id;
  private String name;
  private Double price;
  private Double finalPrice;
  private boolean isApproved;

  public Double getFinalPrice() {
    return finalPrice;
  }

  public void setFinalPrice(Double finalPrice) {
    this.finalPrice = finalPrice;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public boolean isApproved() {
    return isApproved;
  }

  public void setApproved(boolean isApproved) {
    this.isApproved = isApproved;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Product(int id, String name, Double price) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.finalPrice = price;
    this.isApproved = false;
  }

  @Override
  public String toString() {
    return "Product{id=" + id + ", name=" + name + ", isApproved=" + isApproved + ", price=" + price + ", finalPrice=" + finalPrice + "}";
  }
}

class Tax {
  private Double taxValue;

  public Tax(Double taxValue) {
    this.taxValue = taxValue;
  }

  public Double getTaxValue() {
    return taxValue;
  }

  public void setTaxValue(Double taxValue) {
    this.taxValue = taxValue;
  }

  public Double applyTaxes(double price) {
    double taxValue = price * this.taxValue / 100;

    double result = price + taxValue;

    BigDecimal bigDecimal = new BigDecimal(result).setScale(2, RoundingMode.CEILING);

    return bigDecimal.doubleValue();
  }
}
