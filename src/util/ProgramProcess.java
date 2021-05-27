package util;

import commands.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class is responsible for the application process.
 */
public class ProgramProcess {

    private final HashMap<String, Command> commands = new HashMap<>();
    private final CollectionManager collectionManager;
    private final VehicleCreator vehicleCreator;
    private final ArrayList<String> path = new ArrayList<>();

    public ProgramProcess(CollectionManager collectionManager, VehicleCreator vehicleCreator) {
        this.collectionManager = collectionManager;
        this.vehicleCreator = vehicleCreator;
    }

    /**
     * Asks to enter the name of the command and executes necessary command.
     */
    public void process() {
            collectionManager.loadCollection();
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Программа запущена в интерактивном режиме. Введите \"help\", чтобы посмотреть доступные команды.");
        try {
            while (scanner.hasNextLine()) {
                createCommandsMap(commands);
                String line = scanner.nextLine().trim();
                System.out.println();
                if (commands.containsKey(new Command().commandName(line).toLowerCase())) {
                    commands.get(new Command().commandName(line).toLowerCase()).execute(line);
                   // collectionManager.addToHistory(line);
                } else {
                    System.out.println("Команда не найдена. Введите \"help\" для справки.");
                }
                System.out.println();
                System.out.println("Введите команду.");
            }
        } catch (NoSuchElementException ex) {
            System.out.println();
            System.out.println("Завершение работы программы.");
            System.exit(0);
        }
    }

    /**
     * Creates HashMap, where key is the name of the command, value is the class instance of the appropriate command.
     *
     * @param commands HashMap
     */
    public void createCommandsMap(HashMap<String, Command> commands) {
        commands.put("add", new AddCommand(collectionManager, vehicleCreator));
        commands.put("add_if_max", new AddIfMaxCommand(collectionManager, vehicleCreator));
        commands.put("add_if_min", new AddIfMinCommand(collectionManager, vehicleCreator));
        commands.put("clear", new ClearCommand(collectionManager));
        commands.put("execute_script", new ExecuteScriptCommand(this, path));
        commands.put("exit", new ExitCommand(collectionManager));
        commands.put("filter_less_than_fuel_type", new FilterFuelTypeCommand(collectionManager));
        commands.put("help", new HelpCommand(collectionManager));
        commands.put("history", new HistoryCommand(collectionManager));
        commands.put("info", new InfoCommand(collectionManager));
        commands.put("print_field_ascending_distance_travelled", new PrintDistanceTravelledCommand(collectionManager));
        commands.put("remove_by_id", new RemoveByIdCommand(collectionManager));
        commands.put("remove_any_by_fuel_type", new RemoveByFuelTypeCommand(collectionManager));
        commands.put("save", new SaveCommand(collectionManager));
        commands.put("show", new ShowCommand(collectionManager));
        commands.put("update", new UpdateCommand(collectionManager, vehicleCreator));
    }
}