package commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class is responsible for giving information about all the commands in the application.
 */
public class HelpCommand extends Command {

    public HelpCommand() {
        super("help");
    }

    /**
     * Executes the command.
     *
     * @param EnteredCommand the full name of the entered command
     */
    @Override
    public void execute(String EnteredCommand) {
        if (checkCommand(EnteredCommand)) {
            HashMap<String, String> commands = new HashMap<>();
            commands.put("add {element}", "добавить новый элемент в коллекцию");
            commands.put("add_if_max {element}", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
            commands.put("add_if_min {element}", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
            commands.put("clear", "очистить коллекцию");
            commands.put("execute_script file_name", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
            commands.put("exit ", " завершить программу (без сохранения в файл)");
            commands.put("filter_less_than_fuel_type fuelType", "вывести элементы, значение поля fuelType которых меньше заданного");
            commands.put("help", "вывести справку по доступным командам");
            commands.put("history", "вывести историю использованных команд");
            commands.put("info", "Вывести информацию о коллекции");
            commands.put("print_field_ascending_distance_travelled", "вывести значения поля distanceTravelled всех элементов в порядке возрастания");
            commands.put("remove_by_id id", "удалить элемент из коллекции по его id");
            commands.put("remove_any_by_fuel_type fuelType", "удалить из коллекции один элемент, значение поля fuelType которого эквивалентно заданному");
            commands.put("save", "сохранить коллекцию в файл");
            commands.put("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
            commands.put("update id {element}", "обновить значение элемента коллекции, id которого равен заданному");
            Set<Map.Entry<String, String>> pairs = commands.entrySet();
            for (Map.Entry<String, String> i : pairs) {
                System.out.println(i.getKey() + ": " + i.getValue());
            }
        } else {
            System.out.println("Команда не найдена. Введите \"help\" для справки.");
        }
    }
}

