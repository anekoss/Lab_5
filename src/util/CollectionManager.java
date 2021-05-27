package util;

import data.FuelType;
import data.Vehicle;
import exceptions.HistoryIsEmptyException;
import exceptions.IncorrectFieldException;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;


/**
 * This class is responsible for the working with the collection.
 */
public class CollectionManager {
    private final int COMMAND_HISTORY_MAX_VALUE = 13;

    private final String[] commandHistory = new String[COMMAND_HISTORY_MAX_VALUE];

    private Integer maxId;
    private LocalDate date;
    private final FileManager fileManager;
    private final List<Vehicle> sortVehicleByDistanceTravelled = new ArrayList<>();
    private LinkedHashSet<Vehicle> linkedHashSet = new LinkedHashSet<>();

    /**
     * Adds a command to your history
     *
     * @param commandToAdd command
     */
    public void addToHistory(String commandToAdd) {
        for (int i = COMMAND_HISTORY_MAX_VALUE - 1; i > 0; i--) {
            commandHistory[i] = commandHistory[i - 1];
        }
        commandHistory[0] = commandToAdd.split(" ")[0];
    }

    /**
     * Print the last 13 commands used
     */
    public void history() throws HistoryIsEmptyException {
        if (commandHistory[0] == null) throw new HistoryIsEmptyException();
        System.out.println("Последние использованные команды:");
        for (String command : commandHistory) {
            if (command != null) {
                System.out.println(" " + command);
            }
        }
    }

    /**
     * Returns the creation date of this collection.
     *
     * @return the creation date of this collection
     */
    public LocalDate getDate() {
        return date;
    }

    public CollectionManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    /**dto
     *
     *
     * Determines type of this collection.
     *
     * @return type of this collection as a String
     */
    public String collectionType() {
        return linkedHashSet.getClass().getName();
    }

    /**
     * Returns the number of the components in this collection.
     *
     * @return the number of the components in this collection
     */
    public int collectionSize() {
        return linkedHashSet.size();
    }

    /**
     * Returns the collection.
     *
     * @return the collection
     */
    public LinkedHashSet<Vehicle> getCollection() {
        return linkedHashSet;
    }

    /**
     * Shows of an element in string format
     */
    public void stringCollection() {
        if (linkedHashSet.isEmpty()) {
            System.out.println("Коллекция пуста.");
            return;
        }
        for (Vehicle vehicle1 : linkedHashSet) {
            System.out.println(vehicle1.toString());
        }
    }

    /**
     * Appends the specified element to the end of this collection.
     */
    public void addToCollection(Vehicle vehicle) {
        if (maxId != Integer.MAX_VALUE) {
            boolean checkVehicle = false;
            for (Vehicle vehicle1 : linkedHashSet) {
                if (vehicle.vehicleHave(vehicle1)) {
                    checkVehicle = true;
                }
            }
            if (!checkVehicle) {
                linkedHashSet.add(vehicle);
                maxId++;
                vehicle.setId(maxId);
            }
        } else System.out.println("Невозможно добавить элемент в коллекцию!");
    }

    /**
     * Returns the maximum component of this collection.
     *
     * @return the maximum component of this collection
     */
    public Vehicle maxElement() {
        Vehicle maxElement = null;
        for (Vehicle vehicle : linkedHashSet) {
            maxElement = vehicle;
            break;
        }
        for (Vehicle vehicle : linkedHashSet) {
            if (vehicle.compareTo(maxElement) > 0) {
                maxElement = vehicle;
            }
        }
        return maxElement;
    }

    /**
     * Shows elements that have a value of the FuelType field less than the specified value
     *
     * @param fuelType fuel type
     */
    public void filterByFuelType(FuelType fuelType) {
        for (Vehicle vehicle1 : linkedHashSet) {
            if (vehicle1.getFuelType() != null) {
                if (vehicle1.getFuelType().compareTo(fuelType) < 0) {
                    System.out.println(vehicle1.toString());

                }
            }
        }
    }

    /**
     * Returns the min component of this collection.
     *
     * @return the min component of this collection
     */
    public Vehicle minElement() {
        Vehicle minElement = null;
        for (Vehicle vehicle : linkedHashSet) {
            minElement = vehicle;
            break;
        }
        for (Vehicle vehicle : linkedHashSet) {
            if (vehicle.compareTo(minElement) < 0) {
                minElement = vehicle;
            }
        }
        return minElement;
    }

    /**
     * Removes all of the elements from this collection.
     */
    public void clear() {
        linkedHashSet.clear();
    }

    /**
     * Saves a collection to a file
     */
    public void safeToFile() {
        fileManager.writeCollection(linkedHashSet);
    }

    /**
     * Loads the collection from the file.
     */
    public void loadCollection() {
        linkedHashSet = fileManager.readCollection();
        date = LocalDate.now();
        if (fileManager.getMaxId() != null) {
            maxId = fileManager.getMaxId();
        } else maxId = 0;
    }

    /**
     * Tests if this collection has no components.
     *
     * @return true if this vector has no components; false otherwise
     */
    public boolean isEmpty() {
        return linkedHashSet.isEmpty();
    }

    /**
     * Checks if there is an element with the same id
     *
     * @param id id
     * @return presence of a collection item with the same id
     */
    public boolean comparingId(Integer id) {
        for (Vehicle vehicle1 : linkedHashSet) {
            if (id.equals(vehicle1.getId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if there is an element with the same fuelType
     *
     * @param fuelType fuel type
     * @return presence of a collection item with the same fuelType
     */
    public boolean comparingFuelType(FuelType fuelType) throws IncorrectFieldException {
        for (Vehicle vehicle1 : linkedHashSet) {
            if (fuelType.equals(vehicle1.getFuelType())) {
                return true;
            }
        }
        throw new IncorrectFieldException();
    }

    /**
     * Removes the element with the specified fuel type from this collection.
     *
     * @param fuelType fuel value
     */
    public void removeFuelType(FuelType fuelType) {
        for (Vehicle vehicle1 : linkedHashSet) {
            if (vehicle1.getFuelType() != null) {
                if (vehicle1.getFuelType().equals(fuelType)) {
                    linkedHashSet.remove(vehicle1);
                    break;
                }
            }
        }
    }

    /**
     * Deletes an item if its id matches the parameter
     *
     * @param id id
     */
    public void removeItem(Integer id) {
        linkedHashSet.removeIf(vehicle1 -> id.equals(vehicle1.getId()));
    }

    /**
     * Shows the collection fields distanceTravelled sorted in ascending order
     */
    public void sortVehicleByDistanceTravelled() {
        for (Vehicle vehicle : linkedHashSet) {
            if (vehicle.getDistanceTravelled() != null) {
                sortVehicleByDistanceTravelled.add(vehicle);
            }
        }
        sortVehicleByDistanceTravelled.sort(Vehicle.COMPARE_BY_DISTANCE_TRAVELLED);
        for (Vehicle vehicle1 : sortVehicleByDistanceTravelled) {
            if (vehicle1.getDistanceTravelled() != null) {
                System.out.println(vehicle1.getDistanceTravelled());
            }
        }
        sortVehicleByDistanceTravelled.clear();
    }

    public void sortVehicleById() {
        sortVehicleByDistanceTravelled.addAll(linkedHashSet);
        sortVehicleByDistanceTravelled.sort(Vehicle.COMPARE_BY_ID);
        linkedHashSet.clear();
        linkedHashSet.addAll(sortVehicleByDistanceTravelled);
    }


    /**
     * Updates the element of this collection with the specified if value.
     *
     * @param id id value
     */
    public void updateById(Vehicle vehicle, Integer id) {
        vehicle.setId(id);
        linkedHashSet.add(vehicle);
    }
}
