package commands;

/**
 * This class is responsible for the completing the programme.
 */
public class ExitCommand extends Command {

    public ExitCommand() {
        super("exit");
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
        } else {
            System.out.println("Команда не найдена. Введите \"help\" для справки.");
        }
    }
}
