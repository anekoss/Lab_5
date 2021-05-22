package commands;

import data.FuelType;
import util.CollectionManager;

import java.util.regex.Pattern;

/**
 * This class is used to output elements whose FuelType field values are less than the specified value.
 */
public class FilterFuelTypeCommand extends Command {
    private final CollectionManager collectionManager;
    private FuelType fuelType;

    public FilterFuelTypeCommand(CollectionManager collectionManager) {
        super("filter_less_than_fuel_type");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     *
     * @param EnteredCommand The full name of the entered command.
     */
    @Override
    public void execute(String EnteredCommand) {
        FuelType argument1 = null;
        boolean checkposition = false;
        if (checkCommand(EnteredCommand)) {
            if (collectionManager.isEmpty()) {
                System.out.println("Коллекция пуста.");
            } else {
                for (FuelType position1 : fuelType.values()) {
                    if (argument(EnteredCommand).equals(position1.name())) {
                        argument1 = FuelType.valueOf(position1.name());
                        checkposition = true;
                    }
                }
                if (!checkposition) {
                    System.out.println("аргумент должен быть типом FuelType.");
                } else {
                    collectionManager.addToHistory("filter_less_than_fuel_type");
                    collectionManager.filterByFuelType(argument1);
                }
            }
        } else {
            System.out.println("Команда не найдена. Введите \"help\" для справки.");
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
        Pattern pattern = Pattern.compile("^filter_less_than_fuel_type(\\s\\w+)$", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(EnteredCommand).find();
    }
}
