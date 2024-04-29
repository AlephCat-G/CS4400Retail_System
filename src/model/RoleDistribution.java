package model;

public class RoleDistribution {
    private String category;
    private int total;

    public RoleDistribution(String category, int total) {
        this.category = category;
        this.total = total;
    }

    public String getCategory() {
        return category;
    }

    public int getTotal() {
        return total;
    }
}
