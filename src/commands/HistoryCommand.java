package commands;

import exceptions.HistoryIsEmptyException;
import util.CollectionManager;

/**
 * This class is for the output of the 13 most recently used commands..
 */
public class HistoryCommand extends Command {
    private final CollectionManager collectionManager;

    public HistoryCommand(CollectionManager collectionManager) {
        super("history");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     *
     * @param EnteredCommand The full name of the entered command.
     */
    @Override
    public void execute(String EnteredCommand) {
        if (!checkCommand(EnteredCommand)) {
            System.out.println("Команда не найдена. Повторите ввод.");
        } else
            try {
                collectionManager.addToHistory("history");
                collectionManager.history(EnteredCommand);
            } catch (HistoryIsEmptyException e) {
                System.out.println("Ни одной команды еще не было использовано!");
            }
    }
}
