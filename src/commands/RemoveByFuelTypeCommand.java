package commands;

import data.FuelType;
import util.CollectionManager;

import java.util.regex.Pattern;

/**
 * This class is responsible for the removing one element from the collection, fuel type of which
 * coincides with the entered fuel type.
 */
public class RemoveByFuelTypeCommand extends Command {
    private final CollectionManager collectionManager;
    private FuelType fuelType;

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
                    fuelType = FuelType.valueOf(argument(EnteredCommand).toUpperCase());
                    if (collectionManager.comparingFuelType(fuelType)) { //!!! подумкать
                        collectionManager.removeFueltype(fuelType);
                        System.out.println("Элемент успешно удален.");
                    }
                } catch (IllegalArgumentException e) {
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

    public boolean checkFuelType(String EnteredCommand) {
        boolean checkValue = true;
        try {
            FuelType.valueOf(EnteredCommand);
        } catch (IllegalArgumentException ex) {
            System.out.println("Команда не найдена. Введите \"help\" для справки.");
            checkValue = false;
        }
        return checkValue;
    }
}