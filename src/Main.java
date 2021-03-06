import util.CollectionManager;
import util.FileManager;
import util.ProgramProcess;
import util.VehicleCreator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        //System.out.println("Программа запущена в интерактивном режиме. Введите имя файла");
//        String line = scanner.nextLine();
        try {
            String fileName = args[0];
            VehicleCreator vehicleCreator = new VehicleCreator();
            FileManager fileManager = new FileManager(fileName);
            CollectionManager collectionManager = new CollectionManager(fileManager);
            ProgramProcess programProcess = new ProgramProcess(collectionManager, vehicleCreator);
            programProcess.process();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Имя файла не обнаружено.");
        }
    }
}


