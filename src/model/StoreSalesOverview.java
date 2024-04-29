package model;

public class StoreSalesOverview {
    private String storeId;
    private String sname;
    private String manager;
    private int revenue;
    private int incomingRevenue;
    private int incomingOrders;

    public StoreSalesOverview(String storeId, String sname, String manager, 
                              int revenue, int incomingRevenue, int incomingOrders) {
        this.storeId = storeId;
        this.sname = sname;
        this.manager = manager;
        this.revenue = revenue;
        this.incomingRevenue = incomingRevenue;
        this.incomingOrders = incomingOrders;
    }

    public String getStoreId() {
        return storeId;
    }

    public String getSname() {
        return sname;
    }

    public String getManager() {
        return manager;
    }

    public int getRevenue() {
        return revenue;
    }

    public int getIncomingRevenue() {
        return incomingRevenue;
    }

    public int getIncomingOrders() {
        return incomingOrders;
    }
}
