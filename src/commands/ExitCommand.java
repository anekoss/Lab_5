package commands;

import util.CollectionManager;

/**
 * This class is responsible for the completing the programme.
 */
public class ExitCommand extends Command {
    private final CollectionManager collectionManager;
    public ExitCommand(CollectionManager collectionManager) {
        super("exit");
        this.collectionManager= collectionManager;
    }

    /**
     * Executes the command.
     *
     * @param EnteredCommand the full name of the entered command
     */
    @Override
    public void execute(String EnteredCommand) {
        if (checkCommand(EnteredCommand)) {
            System.exit(0);
            collectionManager.addToHistory("exit");
        } else {
            System.out.println("Команда не найдена. Введите \"help\" для справки.");
        }
    }
}
