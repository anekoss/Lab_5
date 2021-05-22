package commands;

import data.Vehicle;
import util.CollectionManager;
import util.VehicleCreator;

/**
 * This class is responsible for the adding an element to the collection
 * if this element is less than the min element in the collection.
 */
public class AddIfMinCommand extends Command {
    private final CollectionManager collectionManager;
    private final VehicleCreator vehicleCreator;

    public AddIfMinCommand(CollectionManager collectionManager, VehicleCreator vehicleCreator) {
        super("add_if_min");
        this.collectionManager = collectionManager;
        this.vehicleCreator = vehicleCreator;
    }

    /**
     * Executes the command.
     *
     * @param EnteredCommand the full name of the entered command
     */
    @Override
    public void execute(String EnteredCommand) {
        if (!checkCommand(EnteredCommand)) {
            System.out.println("Команда не найдена. Введите \"help\" для справки.");
        } else {
            Vehicle vehicle = vehicleCreator.createElement();
            if (collectionManager.isEmpty()) {
                collectionManager.addToCollection(vehicle);
                collectionManager.addToHistory("add_if_min");
                System.out.println("Элемент успешно добавлен в коллекцию.");
            } else if (vehicle.compareTo(collectionManager.minElement()) < 0) {
                collectionManager.addToCollection(vehicle);
                collectionManager.addToHistory("add_if_min");
                System.out.println("Элемент успешно добавлен в коллекцию.");
            }
        }
    }

    @Override
    public void execute(String[] fields) {
        Vehicle vehicle = vehicleCreator.createFromScript(fields);
        if (collectionManager.isEmpty() && vehicle != null) {
            collectionManager.addToCollection(vehicle);
            collectionManager.addToHistory("add_if_min");
            System.out.println("Элемент успешно добавлен в коллекцию.");
        } else if (vehicle != null && vehicle.compareTo(collectionManager.minElement()) < 0) {
            collectionManager.addToCollection(vehicle);
            collectionManager.addToHistory("add_if_min");
            System.out.println("Элемент успешно добавлен в коллекцию.");
        }
        System.out.println();
    }
}
