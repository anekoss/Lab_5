package commands;

//import data.Vehicle;
import util.CollectionManager;

//import java.util.regex.Pattern;

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
                collectionManager.addToHistory("print_field_ascending_distance_travelled");
                collectionManager.sortVehicleByDistanceTravelled();
            }
        } else {
            System.out.println("Команда не найдена. Введите \"help\" для справки.");
        }
    }

}
