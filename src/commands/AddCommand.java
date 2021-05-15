package commands;

import data.Vehicle;
import util.CollectionManager;
import util.VehicleCreator;

/**
 * This class is responsible for the adding an element to the collection.
 */
public class AddCommand extends Command {
    private final CollectionManager collectionManager;
    private final VehicleCreator vehicleCreator;

    public AddCommand(CollectionManager collectionManager, VehicleCreator vehicleCreator) {
        super("add");
        this.collectionManager = collectionManager;
        this.vehicleCreator = vehicleCreator;
    }

    /**
     * Executes the command.
     *
     * @param EnteredCommand the full name of the command
     */
    @Override
    public void execute(String EnteredCommand) {
        if (checkCommand(EnteredCommand)) {
            collectionManager.addToCollection(vehicleCreator.createElement());
            System.out.println("Элемент успешно добавлен в коллекцию.");
        } else {
            System.out.println("Команда не найдена. Введите \"help\" для справки.");
        }
    }

    @Override
    public void execute(String[] fields) {
        Vehicle vehicle = vehicleCreator.createFromScript(fields);
        if (vehicle != null) {
            collectionManager.addToCollection(vehicle);
            System.out.println("Элемент успешно добавлен в коллекцию.");
        } else {
            System.out.println("Элемент не может быть добавлен в коллекцию.");
        }
        System.out.println();
    }
}
