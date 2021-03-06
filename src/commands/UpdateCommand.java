package commands;

import data.Vehicle;
//import exceptions.NotFindIdException;
import util.CollectionManager;
import util.VehicleCreator;

import java.util.regex.Pattern;

/**
 * This class is responsible for the updating the element, id of which coincides with the entered id.
 */
public class UpdateCommand extends Command {
    private final CollectionManager collectionManager;
    private final VehicleCreator vehicleCreator;

    public UpdateCommand(CollectionManager collectionManager, VehicleCreator vehicleCreator) {
        super("update");
        this.collectionManager = collectionManager;
        this.vehicleCreator = vehicleCreator;
    }

    /**
     * Executes the command.
     *
     * @param EnteredCommand the full name of the entered command
     */
    public void execute(String EnteredCommand) {
        if (!checkCommand(EnteredCommand)) {
            System.out.println("Команда не найдена. Повторите ввод");
        } else {
            if (collectionManager.isEmpty()) {
                System.out.println("Невозможно выполнить данную команду, так как коллекция пуста");
            } else {
                boolean checkUpdating = true;
                Integer id = Integer.parseInt(argument(EnteredCommand));
                if (collectionManager.comparingId(id)) {
                    collectionManager.updateById(vehicleCreator.createElement(), id);
                    collectionManager.sortVehicleById();
                    checkUpdating = false;
                }
                if (checkUpdating) {
                    System.out.println("Элемент c таким id не найден :(");
                } else {
                    collectionManager.addToHistory("update");
                }
            }
        }
    }


    /**
     * Checks whether the name of the argument is right or not.
     *
     * @param EnteredCommand the full name of the entered command
     * @return true if the name is not right; false otherwise
     */
    @Override
    public boolean checkCommand(String EnteredCommand) {
        Pattern pattern = Pattern.compile("^update(\\s\\d+)$", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(EnteredCommand).find();
    }

    @Override
    public void execute(String[] fields, Integer id) {
        if (collectionManager.isEmpty()) {
            System.out.println("Невозможно выполнить данную команду, так как коллекция пуста");
        } else {
            boolean checkUpdating = true;
            boolean checkVehicle = true;
            if (collectionManager.comparingId(id)) {
                Vehicle vehicle = vehicleCreator.createFromScript(fields);
                if (vehicle != null) {
                    //collectionManager.removeItem(id);
                    collectionManager.updateById(vehicle, id);
                    collectionManager.sortVehicleById();
                    checkVehicle = false;
                }
                //System.out.println("Невозможно выполнить данную команду, так как в коллекции нет элемента с таким значением id");
                if (checkVehicle) {
                    System.out.println("Невозможно добавить данный элемент в коллекцию.");
                } else {
                    collectionManager.addToHistory("update");
                }
            } else {
                System.out.println("Невозможно выполнить данную команду, так как в коллекции нет элемента с таким значением id");
            }
            System.out.println();
        }
    }
}


