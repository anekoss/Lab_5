package commands;

import util.CollectionManager;

/**
 * This class is responsible for giving information about the collection.
 */
public class InfoCommand extends Command {
    private final CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        super("info");
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
            collectionManager.addToHistory("info");
            System.out.println("Тип коллекции: " + collectionManager.collectionType());
            System.out.println("Дата инициализации коллекции: " + collectionManager.getDate());
            System.out.printf("Количество элементов коллекции: %d\n", collectionManager.collectionSize());
        } else {
            System.out.println("Команда не найдена. Введите \"help\" для справки.");
        }
    }
}
