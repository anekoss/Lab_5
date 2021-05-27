package commands;

import data.FuelType;
import exceptions.IncorrectFieldException;
import util.CollectionManager;

import java.util.regex.Pattern;

/**
 * This class is responsible for the removing one element from the collection, fuel type of which
 * coincides with the entered fuel type.
 */
public class RemoveByFuelTypeCommand extends Command {
    private final CollectionManager collectionManager;

    public RemoveByFuelTypeCommand(CollectionManager collectionManager) {
        super("remove_any_by_fuel_type");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     *
     * @param EnteredCommand the full name of the entered command.
     */
    @Override
    public void execute(String EnteredCommand) {
        if (!checkCommand(EnteredCommand)) {
            System.out.println("Команда не найдена. Введите \"help\" для справки.");
        } else {
            if (collectionManager.isEmpty()) {
                System.out.println("Невозможно выполнить данную команду, так как коллекция пуста.");
            } else {
                try {
                    FuelType fuelType = FuelType.valueOf(argument(EnteredCommand).toUpperCase());
                    if (collectionManager.comparingFuelType(fuelType)) { //!!! подумкать
                        collectionManager.removeFuelType(fuelType);
                        System.out.println("Элемент успешно удален.");
                        collectionManager.addToHistory("remove_any_by_fuel_type");
                    }
                } catch ( IncorrectFieldException e) {
                    System.out.println("Элемент c таким fuel type не найден!");
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
        Pattern pattern = Pattern.compile("^remove_any_by_fuel_type(\\s\\w+)$", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(EnteredCommand).find();
    }

}
