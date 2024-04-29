package model;

public class DronePilotRoster {
    private String pilot;
    private String licenseID;
    private String droneServesStore;
    private int droneTag;
    private int successfulDeliveries;
    private int pendingDeliveries;

    public DronePilotRoster(String pilot, String licenseID, String droneServesStore,
                            int droneTag, int successfulDeliveries, int pendingDeliveries) {
        this.pilot = pilot;
        this.licenseID = licenseID;
        this.droneServesStore = droneServesStore;
        this.droneTag = droneTag;
        this.successfulDeliveries = successfulDeliveries;
        this.pendingDeliveries = pendingDeliveries;
    }

    public String getPilot() {
        return pilot;
    }

    public void setPilot(String pilot) {
        this.pilot = pilot;
    }

    public String getLicenseID() {
        return licenseID;
    }

    public void setLicenseID(String licenseID) {
        this.licenseID = licenseID;
    }

    public String getDroneServesStore() {
        return droneServesStore;
    }

    public void setDroneServesStore(String droneServesStore) {
        this.droneServesStore = droneServesStore;
    }

    public int getDroneTag() {
        return droneTag;
    }

    public void setDroneTag(int droneTag) {
        this.droneTag = droneTag;
    }

    public int getSuccessfulDeliveries() {
        return successfulDeliveries;
    }

    public void setSuccessfulDeliveries(int successfulDeliveries) {
        this.successfulDeliveries = successfulDeliveries;
    }

    public int getPendingDeliveries() {
        return pendingDeliveries;
    }

    public void setPendingDeliveries(int pendingDeliveries) {
        this.pendingDeliveries = pendingDeliveries;
    }
}
