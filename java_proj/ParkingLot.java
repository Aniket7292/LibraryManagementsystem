import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private String parkingLotId;
    private int numberOfFloors;
    private int slotsPerFloor;
    private List<List<Slot>> slots;

    public ParkingLot(String parkingLotId, int numberOfFloors, int slotsPerFloor) {
        this.parkingLotId = parkingLotId;
        this.numberOfFloors = numberOfFloors;
        this.slotsPerFloor = slotsPerFloor;
        this.slots = new ArrayList<>();

        for (int i = 0; i < numberOfFloors; i++) {
            List<Slot> floorSlots = new ArrayList<>();
            for (int j = 0; j < slotsPerFloor; j++) {
                String vehicleType = determineVehicleTypeForSlot(j);
                floorSlots.add(new Slot(i + 1, j + 1, vehicleType));
            }
            slots.add(floorSlots);
        }
    }

    private String determineVehicleTypeForSlot(int slotIndex) {
        if (slotIndex == 0) {
            return "Truck";
        } else if (slotIndex == 1 || slotIndex == 2) {
            return "Bike";
        } else {
            return "Car";
        }
    }

    public String parkVehicle(Vehicle vehicle) {
        for (List<Slot> floorSlots : slots) {
            for (Slot slot : floorSlots) {
                if (slot.canPark(vehicle)) {
                    slot.park(vehicle);
                    return generateTicket(slot);
                }
            }
        }
        return null;
    }

    public boolean unparkVehicle(String ticketId) {
        String[] parts = ticketId.split("_");
        if (parts.length != 3 || !parts[0].equals(parkingLotId)) {
            return false;
        }

        int floorNumber;
        int slotNumber;
        try {
            floorNumber = Integer.parseInt(parts[1]);
            slotNumber = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            return false;
        }

        if (floorNumber < 1 || floorNumber > numberOfFloors || slotNumber < 1 || slotNumber > slotsPerFloor) {
            return false;
        }

        Slot slot = slots.get(floorNumber - 1).get(slotNumber - 1);
        if (slot.isAvailable()) {
            return false;
        }

        slot.vacate();
        return true;
    }

    public int getAvailableSlotsCount(String vehicleType) {
        int count = 0;
        for (List<Slot> floorSlots : slots) {
            for (Slot slot : floorSlots) {
                if (slot.getAllowedVehicleType().equalsIgnoreCase(vehicleType) && slot.isAvailable()) {
                    count++;
                }
            }
        }
        return count;
    }

    public List<Slot> getAvailableSlots(String vehicleType) {
        List<Slot> availableSlots = new ArrayList<>();
        for (List<Slot> floorSlots : slots) {
            for (Slot slot : floorSlots) {
                if (slot.getAllowedVehicleType().equalsIgnoreCase(vehicleType) && slot.isAvailable()) {
                    availableSlots.add(slot);
                }
            }
        }
        return availableSlots;
    }

    public List<Slot> getOccupiedSlots(String vehicleType) {
        List<Slot> occupiedSlots = new ArrayList<>();
        for (List<Slot> floorSlots : slots) {
            for (Slot slot : floorSlots) {
                if (slot.getAllowedVehicleType().equalsIgnoreCase(vehicleType) && !slot.isAvailable()) {
                    occupiedSlots.add(slot);
                }
            }
        }
        return occupiedSlots;
    }

    private String generateTicket(Slot slot) {
        return parkingLotId + "_" + slot.getFloorNumber() + "_" + slot.getSlotNumber();
    }
}