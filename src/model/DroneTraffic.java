package model;

public class DroneTraffic {
    private String droneServesStore;
    private String droneTag;
    private String pilot;
    private int totalWeightAllowed;
    private int currentWeight;
    private int deliveriesAllowed;
    private int deliveriesInProgress;

    public DroneTraffic(String droneServesStore, String droneTag, String pilot,
                        int totalWeightAllowed, int currentWeight,
                        int deliveriesAllowed, int deliveriesInProgress) {
        this.droneServesStore = droneServesStore;
        this.droneTag = droneTag;
        this.pilot = pilot;
        this.totalWeightAllowed = totalWeightAllowed;
        this.currentWeight = currentWeight;
        this.deliveriesAllowed = deliveriesAllowed;
        this.deliveriesInProgress = deliveriesInProgress;
    }

    // Getters
    public String getDroneServesStore() {
        return droneServesStore;
    }

    public String getDroneTag() {
        return droneTag;
    }

    public String getPilot() {
        return pilot;
    }

    public int getTotalWeightAllowed() {
        return totalWeightAllowed;
    }

    public int getCurrentWeight() {
        return currentWeight;
    }

    public int getDeliveriesAllowed() {
        return deliveriesAllowed;
    }

    public int getDeliveriesInProgress() {
        return deliveriesInProgress;
    }
}
