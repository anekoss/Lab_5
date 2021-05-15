package commands;

import data.Vehicle;
import util.CollectionManager;

import java.util.regex.Pattern;

/**
 * This class is used to output the values of the distance Traveled field of all elements in ascending order
 */
public class PrintDistanceTravelledCommand extends Command {
    private final CollectionManager collectionManager;

    public PrintDistanceTravelledCommand(CollectionManager collectionManager) {
        super("print_field_ascending_distance_travelled");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     *
     * @param EnteredCommand The full name of the entered command.
     */
    @Override
    public void execute(String EnteredCommand) {
        if (checkCommand(EnteredCommand)) {
            if (collectionManager.isEmpty()) {
                System.out.println("Коллекция пуста.");
            } else {
                collectionManager.sortVehicleByDistanceTravelled();
//                for (Vehicle vehicle : collectionManager.getCollection()) {
//                    System.out.println(vehicle.getDistanceTravelled());
//                }
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
        Pattern pattern = Pattern.compile("^print_field_ascending_distance_travelled(\\s\\w+)$", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(EnteredCommand).find();
    }
}
