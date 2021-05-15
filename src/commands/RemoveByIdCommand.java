package commands;

import exceptions.NotFindIdException;
import util.CollectionManager;

import java.util.regex.Pattern;

/**
 * This class is responsible for the removing one element from the collection, id of which
 * coincides with the entered color.
 */
public class RemoveByIdCommand extends Command {
    private final CollectionManager collectionManager;
    private Integer id;

    public RemoveByIdCommand(CollectionManager collectionManager) {
        super("remove_by_id");
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
            System.out.println("Команда не найдена. Введите \"help\" для справки");
        } else {
            if (collectionManager.isEmpty()) {
                System.out.println("Невозможно выполнить данную команду, так как коллекция пуста.");
            } else {
                try {
                    id = Integer.parseInt(argument(EnteredCommand));
                    if (collectionManager.comparingId(id)) { //!!! подумкать
                        collectionManager.removeItem(id);
                        System.out.println("Элемент успешно удален.");
                    }
                } catch (NotFindIdException e) {
                    System.out.println("Элемент c таким id не найден!");
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
        Pattern pattern = Pattern.compile("^remove_by_id(\\s\\d+)$", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(EnteredCommand).find();
    }
}
