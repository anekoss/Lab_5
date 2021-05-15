package commands;

import exceptions.NotFindIdException;
import util.CollectionManager;
import util.ProgramProcess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * This class is responsible for the executing commands from the script.
 */
public class ExecuteScriptCommand extends Command {
    private final HashMap<String, Command> commands = new HashMap<>();
    private final CollectionManager collectionManager;
    private final ProgramProcess programProcess;
    private final ArrayList<String> path;

    public ExecuteScriptCommand(CollectionManager collectionManager, ProgramProcess programProcess, ArrayList<String> path) {
        super("execute_script");
        this.collectionManager = collectionManager;
        this.programProcess = programProcess;
        this.path = path;
    }

    /**
     * Executes the command.
     *
     * @param EnteredCommand the full name of the entered command
     */
    public void execute(String EnteredCommand) {
        if (checkCommand(EnteredCommand)) {
            programProcess.createCommandsMap(commands);
            File file = new File(argument(EnteredCommand));
            if (file.exists() && !file.canRead()) {
                System.out.println("Невозможно выполнить данную команду, так как у указанного файла отсутвуют права на чтение.");
            } else {
                try {
                    String data = null;
                    Scanner scanner = new Scanner(new FileReader(file));
                    if (path.contains(file.getAbsolutePath())) {
                        data = "";
                        System.out.println("Невозможно выполнить команду " + EnteredCommand + ".");
                    } else {
                        path.add((file).getAbsolutePath());
                    }
                    // StringBuilder CommandName = new StringBuilder();
                    while (data != "") {
                        data = scanner.nextLine();
                        String FullCommandName = data.toString().trim().toLowerCase();
                        if (FullCommandName.equals("add")) {
                            System.out.println(FullCommandName);
                            System.out.println();
                            commands.get("add").execute(fieldValues(scanner));
                        } else if (FullCommandName.equals("add_if_max")) {
                            System.out.println(FullCommandName);
                            System.out.println();
                            commands.get("add_if_max").execute(fieldValues(scanner));
                        } else if (new Command().commandName(FullCommandName).equals("update")) {
                            try {
                                Integer id = Integer.parseInt(new Command().argument(FullCommandName));
                                System.out.println(FullCommandName);
                                System.out.println();
                                commands.get("update").execute(fieldValues(scanner), id);
                            } catch (NumberFormatException ex) {
                                System.out.println("Команда " + FullCommandName + " не найдена");
                                System.out.println();
                            } catch (NotFindIdException e) {
                                System.out.println("id не найден");
                                System.out.println();
                            }
                        } else if (commands.containsKey(new Command().commandName(FullCommandName))) {
                            System.out.println(FullCommandName);
                            System.out.println();
                            commands.get(new Command().commandName(FullCommandName)).execute(FullCommandName);
                            System.out.println();
                        } else if (FullCommandName.length() != 0) {
                            System.out.println("Команда " + FullCommandName + " не найдена");
                            System.out.println();
                        }
                        //CommandName.delete(0, CommandName.length());
                        if (!scanner.hasNext()) {
                            path.remove(path.size() - 1);
                            System.out.println("Выполнение скрипта " + argument(EnteredCommand) + " завершено.");
                        }

                    }

                } catch (FileNotFoundException e) {
                    System.out.println("Указанный файл не найден.");
                } catch (NoSuchElementException e) {
                    System.out.println();
                } catch (IOException e) {
                    System.out.println("Ошибка ввода-вывода.");
                }
            }
        } else {
            System.out.println("Команда не найдена. Введите \"help\" для справки.");
        }
    }

    /**
     * Checks whether the name of the argument is right or not.
     *
     * @param EnteredCommand the full name of the entered command.
     * @return true if the name is not right; false otherwise
     */
    @Override
    public boolean checkCommand(String EnteredCommand) {
        Pattern pattern = Pattern.compile("^execute_script(\\s\\S+)$", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(EnteredCommand).find();
    }

    public String[] fieldValues(Scanner scanner) throws IOException {
        String[] fieldValue = new String[7];
        StringBuilder value = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            String data = scanner.nextLine();
            value.append(data);
            String Value = value.toString().trim();
            fieldValue[i] = Value;
            value.delete(0, value.length());
        }
        return fieldValue;
    }
}