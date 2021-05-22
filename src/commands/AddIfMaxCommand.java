package commands;

import data.Vehicle;
import util.CollectionManager;
import util.VehicleCreator;

/**
 * This class is responsible for the adding an element to the collection
 * if this element is greater than the max element in the collection.
 */
public class AddIfMaxCommand extends Command {
    private final CollectionManager collectionManager;
    private final VehicleCreator vehicleCreator;

    public AddIfMaxCommand(CollectionManager collectionManager, VehicleCreator vehicleCreator) {
        super("add_if_max");
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
                collectionManager.addToHistory("add_if_max");

                collectionManager.addToCollection(vehicle);
                System.out.println("Элемент успешно добавлен в коллекцию.");
            } else if (vehicle.compareTo(collectionManager.maxElement()) > 0) {
                collectionManager.addToCollection(vehicle);
                collectionManager.addToHistory("add_if_max");

                System.out.println("Элемент успешно добавлен в коллекцию.");
            }
        }
    }

    @Override
    public void execute(String[] fields) {
        Vehicle vehicle = vehicleCreator.createFromScript(fields);
        if (collectionManager.isEmpty() && vehicle != null) {
            collectionManager.addToCollection(vehicle);

            System.out.println("Элемент успешно добавлен в коллекцию.");
            collectionManager.addToHistory("add_if_max");
        } else if (vehicle != null && vehicle.compareTo(collectionManager.maxElement()) > 0) {
            collectionManager.addToCollection(vehicle);
            collectionManager.addToHistory("add_if_max");
            System.out.println("Элемент успешно добавлен в коллекцию.");
        }
        System.out.println();
    }
}
