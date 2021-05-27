package commands;

//import exceptions.NotFindIdException;

public class Command {
    private String name;

    public Command(String name) {

        this.name = name;
    }

    public Command() {

    }

    public String argument(String EnteredCommand) {
        return EnteredCommand.substring(EnteredCommand.indexOf(" ") + 1);
    }

    public String getName() {
        return name;
    }

    public String commandName(String EnteredCommand) {
        if (!EnteredCommand.contains(" ")) {
            return EnteredCommand;
        } else return EnteredCommand.substring(0, EnteredCommand.indexOf(" "));
    }

    public boolean checkCommand(String command) {

        return command.equals(name);
    }


    public void execute(String EnteredCommand) {
    }

    public void execute(String[] fields) {
    }

    public void execute(String[] fields, Integer value) {
    }

}