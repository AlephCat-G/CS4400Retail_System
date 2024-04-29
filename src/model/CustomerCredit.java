package model;

public class CustomerCredit {
    private String customerName;
    private int rating;
    private int currentCredit;
    private int creditAllocated;

    public CustomerCredit(String customerName, int rating, int currentCredit, int creditAllocated) {
        this.customerName = customerName;
        this.rating = rating;
        this.currentCredit = currentCredit;
        this.creditAllocated = creditAllocated;
    }

    public String getCustomerName() { return customerName; }
    public int getRating() { return rating; }
    public int getCurrentCredit() { return currentCredit; }
    public int getCreditAllocated() { return creditAllocated; }
}
