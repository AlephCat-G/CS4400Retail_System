package model;

public class OrderInProgress {
    private String orderID;
    private int cost;
    private int numProducts;
    private int payload;
    private String contents;

    public OrderInProgress(String orderID, int cost, int numProducts, int payload, String contents) {
        this.orderID = orderID;
        this.cost = cost;
        this.numProducts = numProducts;
        this.payload = payload;
        this.contents = contents;
    }

    public String getOrderID() {
        return orderID;
    }

    public int getCost() {
        return cost;
    }

    public int getNumProducts() {
        return numProducts;
    }

    public int getPayload() {
        return payload;
    }

    public String getContents() {
        return contents;
    }

}
