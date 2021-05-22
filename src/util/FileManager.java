package util;

import data.Coordinates;
import data.FuelType;
import data.Vehicle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class is responsible for the operations with file.
 */
public class FileManager {
    private Integer maxId = null;
    private File file;
    private String filePath;
    private final String envVariable;

    public Integer getMaxId() {
        return maxId;
    }

    public FileManager(String envVariable) {
        this.envVariable = envVariable;
        this.file = new File(envVariable);
    }

    /**
     * Reads the collection from the file.
     *
     * @return vector - the collection from the file
     */
    public LinkedHashSet<Vehicle> readCollection() {
        LinkedHashSet<Vehicle> collectionFromFile = new LinkedHashSet<>();
        String tempName = null;
        Integer tempDistanceTravelled = null;
        float tempEnginePower = 0;
        FuelType tempFuelType = null;
        long tempFuelConsumption = 0;
        double tempX = -385;
        Integer tempY = null;
        Integer tempId = null;
        Vehicle vehicle = null;
        LocalDate tempCreationDate = null;
        try {
            File input = new File(envVariable);
            Scanner scanner = new Scanner(input);
            String file = "";
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains("<name>")) {
                    String tempLine = line;
                    tempLine = tempLine.replace("<name>", "").replace("</name>", "").replace(" ", "");
                    tempName = tempLine;
                    if(tempName==null||tempName.equals("")){
                        System.out.println("Неверное имя для vehicle!");
                        tempName=null;
                    }
                }
                if (line.contains("<id>")) {
                    String tempLine = line;
                    tempLine = tempLine.replace("<id>", "").replace("</id>", "").replace(" ", "");
                    tempId = Integer.parseInt(tempLine);
                    if(tempId<=0||tempId==null){
                        System.out.println("Неверное id для vehicle!");
                        tempId=null;
                    }
                    else {
                        for(Vehicle vehicle1:collectionFromFile){
                            if(tempId.equals(vehicle1.getId())){
                                System.out.println("Неверное id для vehicle!");
                                tempId=null;
                                break;
                            }
                        }
                    }
                }
                if (line.contains("<CreationDate>")) {
                    String tempLine = line;
                    tempLine = tempLine.replace("<CreationDate>", "").replace("</CreationDate>", "").replace(" ", "");
                    tempCreationDate = LocalDate.parse(tempLine);
                    if(tempCreationDate==null){
                        System.out.println("Неверная дата создания для vehicle!");
                    }
                }
                if (line.contains("<enginePower>")) {
                    String tempLine = line;
                    tempLine = tempLine.replace("<enginePower>", "").replace("</enginePower>", "").replace(" ", "");
                    tempEnginePower = Float.parseFloat(tempLine);
                    if(tempEnginePower<=0){
                        System.out.println("Неверная мощность для vehicle!");
                        tempEnginePower=0;
                    }
                }
                if (line.contains("<fuelConsumption>")) {
                    String tempLine = line;
                    tempLine = tempLine.replace("<fuelConsumption>", "").replace("</fuelConsumption>", "").replace(" ", "");
                    tempFuelConsumption = Long.parseLong(tempLine);
                    if(tempFuelConsumption<=0){
                        System.out.println("Неверное fuelConsumption для vehicle!");
                        tempFuelConsumption=0;
                    }
                }
                if (line.contains("<distanceTravelled>")) {
                    String tempLine = line;
                    tempLine = tempLine.replace("<distanceTravelled>", "").replace("</distanceTravelled>", "").replace(" ", "");
                    if (!tempLine.equals("")) {
                        tempDistanceTravelled = Integer.parseInt(tempLine);
                    }
                    if(tempDistanceTravelled<=0)
                    {
                        System.out.println("Неверное пройденное расстояние для vehicle!");
                        tempDistanceTravelled=null;
                    }
                }
                if (line.contains("<FuelType>")) {
                    String tempLine = line;
                    tempLine = tempLine.replace("<FuelType>", "").replace("</FuelType>", "").replace(" ", "");
                    if (!tempLine.equals("")) {
                        tempFuelType = FuelType.valueOf(tempLine);
                    }
                }
                if (line.contains("<x>")) {
                    String tempLine = line;
                    tempLine = tempLine.replace("<x>", "").replace("</x>", "").replace(" ", "");
                    tempX = Double.parseDouble(tempLine);
                    if(tempX<=-385){
                        System.out.println("Неверное x для vehicle!");
                    }
                }
                if (line.contains("<y>")) {
                    String tempLine = line;
                    tempLine = tempLine.replace("<y>", "").replace("</y>", "").replace(" ", "");
                    tempY = Integer.valueOf(tempLine);
                    if(tempY==null){
                        System.out.println("Неверное y для vehicle!");
                    }
                }
                if (line.contains("</Vehicle>")) {
                    if (tempId == null || tempName == null || tempY == null || tempX <=-385 || tempCreationDate == null || tempEnginePower == 0 || tempFuelConsumption == 0) {
                        System.out.println("Worker can't be added. Some data is wrong.");

                    } else {
                        if (tempDistanceTravelled != null && tempFuelType != null) {
                            vehicle = new Vehicle(tempId, tempName, new Coordinates(tempX, tempY), tempCreationDate, tempEnginePower, tempFuelConsumption, tempDistanceTravelled, tempFuelType);
                        } else if (tempDistanceTravelled != null) {
                            vehicle = new Vehicle(tempId, tempName, new Coordinates(tempX, tempY), tempCreationDate, tempEnginePower, tempFuelConsumption, tempDistanceTravelled);
                        } else if (tempFuelType != null) {
                            vehicle = new Vehicle(tempId, tempName, new Coordinates(tempX, tempY), tempCreationDate, tempEnginePower, tempFuelConsumption, tempFuelType);
                        } else {
                            vehicle = new Vehicle(tempId, tempName, new Coordinates(tempX, tempY), tempCreationDate, tempEnginePower, tempFuelConsumption);
                        }
                        collectionFromFile.add(vehicle);
                    }
                    tempName = null;
                    tempDistanceTravelled = null;
                    tempEnginePower = 0;
                    tempFuelType = null;
                    tempFuelConsumption = 0;
                    tempX = 0;
                    tempY = null;
                    tempId = null;
                    tempCreationDate = null;
                    vehicle = null;
                }
            }
            scanner.close();
            System.out.println("Коллекция успешно загружена.");
        } catch (FactoryConfigurationError | IOException exception) {
            System.out.println("Что-то пошло не так. Пожалуйста, исправьте файл и повторите попытку. (" + exception.getMessage() + ")");
            System.exit(0);
        }

        return collectionFromFile;
    }

    /**
     * Transforms the collection to the xml-format and writes it to xml-file.
     *
     * @param linkedHashSet - the collection
     */

    public void writeCollection(LinkedHashSet<Vehicle> linkedHashSet) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.newDocument();
            if (!linkedHashSet.isEmpty()) {
                Element root = document.createElement("Vehicles");
                document.appendChild(root);
                for (Vehicle vehicle : linkedHashSet) {
                    Element call = document.createElement("Vehicle");
                    root.appendChild(call);
                    Element id = document.createElement("id");
                    id.appendChild(document.createTextNode(String.valueOf(vehicle.getId())));
                    call.appendChild(id);
                    Element creationDate = document.createElement("CreationDate");
                    creationDate.appendChild(document.createTextNode(String.valueOf(vehicle.getCreationDate())));
                    call.appendChild(creationDate);
                    Element name = document.createElement("name");
                    name.appendChild(document.createTextNode(vehicle.getName()));
                    call.appendChild(name);
                    Element age = document.createElement("enginePower");
                    age.appendChild(document.createTextNode(String.valueOf(vehicle.getEnginePower())));
                    call.appendChild(age);
                    if (vehicle.getFuelType() != null) {
                        Element type = document.createElement("FuelType");
                        type.appendChild(document.createTextNode(String.valueOf(vehicle.getFuelType())));
                        call.appendChild(type);
                    }
                    Element color = document.createElement("fuelConsumption");
                    color.appendChild(document.createTextNode(String.valueOf(vehicle.getFuelConsumption())));
                    call.appendChild(color);
                    if (vehicle.getDistanceTravelled() != null) {
                        Element character = document.createElement("distanceTravelled");
                        character.appendChild(document.createTextNode(String.valueOf(vehicle.getDistanceTravelled())));
                        call.appendChild(character);
                    }
                    Element coordinates = document.createElement("Coordinates");
                    call.appendChild(coordinates);
                    Element x = document.createElement("x");
                    x.appendChild(document.createTextNode(String.valueOf(vehicle.getCoordinates().getX())));
                    coordinates.appendChild(x);
                    Element y = document.createElement("y");
                    y.appendChild(document.createTextNode(String.valueOf(vehicle.getCoordinates().getY())));
                    coordinates.appendChild(y);
                }
            }
            writeDocument(document);
        } catch (ParserConfigurationException e) {
            System.out.println("Невозможно выполнить данную команду из-за ошибки конфигурации.");
        }
    }

    /**
     * Writes document to the xml-file.
     *
     * @param document - written document
     */
    public void writeDocument(Document document) {
        try {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource dom = new DOMSource(document);
            if (!file.canWrite() && file.exists()) {
                System.out.println();
                System.out.println("Невозможно выполнить данную команду, так как отсутвуют права для записи в файл.");
            } else if (!file.exists()) {
                System.out.println("Невозможно выполнить данную команду, так как файл с таким именем не найден.");
            } else {
                BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
                StreamResult result = new StreamResult(outputStream);
                tr.transform(dom, result);
            }
        } catch (TransformerException ex) {
            System.out.println("Невозможно выполнить данную команду.");
        } catch (FileNotFoundException ex) {
            if (!file.exists()) {
                System.out.println("Невозможно выполнить данную команду, так как файл с таким именем не найден.");
            }
        }
    }
}
