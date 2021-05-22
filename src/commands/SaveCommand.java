package commands;

import util.CollectionManager;

/**
 * This class is responsible for saving the collection to the file.
 */
public class SaveCommand extends Command {
    private final CollectionManager collectionManager;

    public SaveCommand(CollectionManager collectionManager) {
        super("save");
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
                collectionManager.safeToFile();
                System.out.println("Коллекция успешно записана в файл.");
            collectionManager.addToHistory("save");
        } else {
            System.out.println("Команда не найдена. Введите \"help\" для справки.");
        }
    }
}
