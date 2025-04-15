public class Slot {
    private int floorNumber;
    private int slotNumber;
    private String allowedVehicleType;
    private boolean isOccupied;
    private Vehicle parkedVehicle;

    public Slot(int floorNumber, int slotNumber, String allowedVehicleType) {
        this.floorNumber = floorNumber;
        this.slotNumber = slotNumber;
        this.allowedVehicleType = allowedVehicleType;
        this.isOccupied = false;
        this.parkedVehicle = null;
    }

    public boolean isAvailable() {
        return !isOccupied;
    }

    public boolean canPark(Vehicle vehicle) {
        return this.allowedVehicleType.equalsIgnoreCase(vehicle.getType()) && isAvailable();
    }

    public void park(Vehicle vehicle) {
        if (canPark(vehicle)) {
            this.parkedVehicle = vehicle;
            this.isOccupied = true;
        } else {
            throw new IllegalStateException("Cannot park vehicle here.");
        }
    }

    public void vacate() {
        this.parkedVehicle = null;
        this.isOccupied = false;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public String getAllowedVehicleType() {
        return allowedVehicleType;
    }

    public Vehicle getParkedVehicle() {
        return parkedVehicle;
    }
}