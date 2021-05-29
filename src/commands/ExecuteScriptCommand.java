package commands;


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

    private final ProgramProcess programProcess;
    private final ArrayList<String> path;

    public ExecuteScriptCommand(ProgramProcess programProcess, ArrayList<String> path) {
        super("execute_script");
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
                System.out.println();
            } else {
                try {
                    String data = "3";
                    Scanner scanner = new Scanner(new FileReader(file));
                    if (path.contains(file.getAbsolutePath())) {
                        data = null;
                        System.out.println("Невозможно выполнить команду " + EnteredCommand + ".");
                        System.out.println();
                    } else {
                        path.add((file).getAbsolutePath());
                    }
                    while (data!=null) {
                        data = scanner.nextLine();
                        String FullCommandName = data.trim().toLowerCase();
                        if (FullCommandName.equals("add")) {
                            System.out.println(FullCommandName);
                            System.out.println();
                            try {
                                commands.get("add").execute(fieldValues(scanner));
                            } catch (NoSuchElementException e) {
                                System.out.println("Команда не может быть выполнена. Проверьте верность скрипта.");
                                System.out.println();
                            }
                        } else if (FullCommandName.equals("add_if_max")) {
                            System.out.println(FullCommandName);
                            System.out.println();
                            try {
                                commands.get("add_if_max").execute(fieldValues(scanner));
                            } catch (NoSuchElementException e) {

                                System.out.println("Команда не может быть выполнена. Проверьте верность скрипта.");
                                System.out.println();
                            }
                        } else if (new Command().commandName(FullCommandName).equals("update")) {
                            try {
                                int id = Integer.parseInt(new Command().argument(FullCommandName));
                                System.out.println(FullCommandName);
                                System.out.println();
                                if (id > 0) {
                                    try {
                                        commands.get("update").execute(fieldValues(scanner), id);
                                    } catch (NoSuchElementException e) {

                                        System.out.println("Команда не может быть выполнена. Проверьте верность скрипта.");
                                        System.out.println();
                                    }
                                } else {
                                    System.out.println("Неверное id для Vehicle!");
                                    System.out.println();
                                }
                            } catch (NumberFormatException ex) {
                                System.out.println("Команда " + FullCommandName + " не найдена");
                                System.out.println();
                            }
                        } else if (commands.containsKey(new Command().commandName(FullCommandName))) {
                            System.out.println(FullCommandName);
                            System.out.println();
                            try {
                                commands.get(new Command().commandName(FullCommandName)).execute(FullCommandName);
                            } catch (NoSuchElementException e) {
                                System.out.println("Команда не может быть выполнена. Проверьте верность скрипта.");
                                System.out.println();
                            }
                        } else if (FullCommandName.length() != 0) {
                            System.out.println("Команда " + FullCommandName + " не найдена");
                            System.out.println();
                        }
                    if(!scanner.hasNextLine()){
                    path.remove(path.size() - 1);
                    System.out.println();
                    System.out.println("Выполнение скрипта " + argument(EnteredCommand) + " завершено.");
                    }}
                    // data = scanner.nextLine();
                } catch (FileNotFoundException e) {
                    System.out.println("Указанный файл не найден.");
                    System.out.println();
                } catch (NoSuchElementException e) {
                    System.out.println();
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

    public String[] fieldValues(Scanner scanner) {
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