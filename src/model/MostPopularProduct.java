package model;

public class MostPopularProduct {
    // All required properties
    private String barcode;
    private String productName;
    private int weight;
    private int lowestPrice;
    private int highestPrice;
    private int lowestQuantity;
    private int highestQuantity;
    private int totalQuantity;

    // Constructor with all properties
    public MostPopularProduct(String barcode, String productName, int weight,
                              int lowestPrice, int highestPrice, int lowestQuantity,
                              int highestQuantity, int totalQuantity) {
        this.barcode = barcode;
        this.productName = productName;
        this.weight = weight;
        this.lowestPrice = lowestPrice;
        this.highestPrice = highestPrice;
        this.lowestQuantity = lowestQuantity;
        this.highestQuantity = highestQuantity;
        this.totalQuantity = totalQuantity;
    }

    // Getters and setters for each property
    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }
    public int getLowestPrice() { return lowestPrice; }
    public void setLowestPrice(int lowestPrice) { this.lowestPrice = lowestPrice; }
    public int getHighestPrice() { return highestPrice; }
    public void setHighestPrice(int highestPrice) { this.highestPrice = highestPrice; }
    public int getLowestQuantity() { return lowestQuantity; }
    public void setLowestQuantity(int lowestQuantity) { this.lowestQuantity = lowestQuantity; }
    public int getHighestQuantity() { return highestQuantity; }
    public void setHighestQuantity(int highestQuantity) { this.highestQuantity = highestQuantity; }
    public int getTotalQuantity() { return totalQuantity; }
    public void setTotalQuantity(int totalQuantity) { this.totalQuantity = totalQuantity; }
}
