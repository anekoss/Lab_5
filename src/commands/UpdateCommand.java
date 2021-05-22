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
    private Integer id;
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
                id = Integer.parseInt(argument(EnteredCommand));
                if (collectionManager.comparingId(id)) {
                    collectionManager.removeItem(id);
                    collectionManager.updateById(vehicleCreator.createElement(), id);
                    collectionManager.sortVehicleById();
                    checkUpdating = false;
                }
                if (checkUpdating) {
                    System.out.println("Элемент c таким id не найден :(");}
                 else {
                    collectionManager.addToHistory("update");
                    System.out.println("Элемент успешно обновлен");
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
                if (collectionManager.comparingId(id)) {
                    Vehicle vehicle = vehicleCreator.createFromScript(fields);
                    if (vehicle != null) {
                    collectionManager.removeItem(id);
                    collectionManager.updateById(vehicleCreator.createElement(), id);
                    collectionManager.sortVehicleById();
                    checkUpdating = false;
                }
                if (checkUpdating) {
                    System.out.println("Невозможно выполнить данную команду, так как в коллекции нет элемента с таким значением id");
                }else {
                    System.out.println("Элемент с id " + id + " успено обновлен");
                    collectionManager.addToHistory("update");
                }
            } else {
                System.out.println("Невозможно выполнить данную команду");
            }
            System.out.println();
        }
    }
}


