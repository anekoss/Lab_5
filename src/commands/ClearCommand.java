package commands;

import util.CollectionManager;

/**
 * This class is responsible for the removing all of the elements from the collection.
 */
public class ClearCommand extends Command {
    private final CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        super("clear");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     *
     * @param EnteredCommand the full name of the entered command
     */
    @Override
    public void execute(String EnteredCommand) {
        if (checkCommand(EnteredCommand)) {
            collectionManager.clear();
            System.out.println("Коллекция очищена.");
        } else {
            System.out.println("Команда не найдена. Введите \"help\" для справки.");
        }
    }
}

