package commands;

import util.CollectionManager;

/**
 * This class is responsible for giving information about the elements in the collection.
 */
public class ShowCommand extends Command {
    private final CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        super("show");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     *
     * @param EnteredCommand the full name of the entered command
     */
    public void execute(String EnteredCommand) {
        if (checkCommand(EnteredCommand)) {
            collectionManager.stringCollection();
        } else {
            System.out.println("Команда не найдена. Введите \"help\" для справки.");
        }
    }

}

