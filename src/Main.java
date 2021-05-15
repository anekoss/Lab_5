import util.CollectionManager;
import util.FileManager;
import util.ProgramProcess;
import util.VehicleCreator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String fileName = "";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Программа запущена в интерактивном режиме. Введите имя файла");
        String line = scanner.nextLine();
        fileName = line;
        VehicleCreator vehicleCreator = new VehicleCreator();
        FileManager fileManager = new FileManager(fileName);
        CollectionManager collectionManager = new CollectionManager(vehicleCreator, fileManager);
        ProgramProcess programProcess = new ProgramProcess(collectionManager, vehicleCreator);
        collectionManager.loadCollection();
        programProcess.process();
    }
}


