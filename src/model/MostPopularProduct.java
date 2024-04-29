package model;

public class MostPopularProduct {
    private String barcode;
    private String productName;
    private int weight;
    private int lowestPrice;
    private int highestPrice;
    private int lowestQuantity;
    private int highestQuantity;
    private int totalQuantity;

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

    // Getters and setters for barcode
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    // Getters and setters for productName
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    // Getters and setters for weight
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    // Getters and setters for lowestPrice
    public int getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(int lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    // Getters and setters for highestPrice
    public int getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(int highestPrice) {
        this.highestPrice = highestPrice;
    }

    // Getters and setters for lowestQuantity
    public int getLowestQuantity() {
        return lowestQuantity;
    }

    public void setLowestQuantity(int lowestQuantity) {
        this.lowestQuantity = lowestQuantity;
    }

    // Getters and setters for highestQuantity
    public int getHighestQuantity() {
        return highestQuantity;
    }

    public void setHighestQuantity(int highestQuantity) {
        this.highestQuantity = highestQuantity;
    }

    // Getters and setters for totalQuantity
    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    @Override
    public String toString() {
        return "MostPopularProduct{" +
                "barcode='" + barcode + '\'' +
                ", productName='" + productName + '\'' +
                ", weight=" + weight +
                ", lowestPrice=" + lowestPrice +
                ", highestPrice=" + highestPrice +
                ", lowestQuantity=" + lowestQuantity +
                ", highestQuantity=" + highestQuantity +
                ", totalQuantity=" + totalQuantity +
                '}';
    }
}
