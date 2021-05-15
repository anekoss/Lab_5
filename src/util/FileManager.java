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
import java.util.Scanner;

/**
 * This class is responsible for the operations with file.
 */
public class FileManager {
    private Integer maxId = null;
    private File file;
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
        LinkedHashSet<Vehicle> linkedHashSet = new LinkedHashSet<>();
        String tempName = null;
        Integer tempDistanceTravelled = 0;
        float tempEnginePower = 0;
        FuelType tempFuelType = null;
        long tempFuelConsumption = 0;
        double tempX = 0;
        Integer tempY = null;
        Integer tempId = null;
        LocalDate tempCreationDate = null;
        try {
            File input = new File(envVariable);
            Scanner scanner = new Scanner(input);
            StringBuilder file = new StringBuilder();
            HashMap<Long, String> incorrectNames = new HashMap<>();
            long vehicleNumberForNames = 1;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains("<vehicle>")) {
                    vehicleNumberForNames += 1;
                }
                if (line.contains("<name>")) {
                    String tempLine = line;
                    tempLine = tempLine.replace("<name>", "").replace("</name>", "");
                    incorrectNames.put(vehicleNumberForNames, tempLine);
                    tempLine = tempLine.replace("<", "");
                    file.append("<name>").append(tempLine).append("</name>");
                } else {
                    file.append(line);
                }
            }
            scanner.close();
            incorrectNames.replaceAll((s, v) -> v.replace(" ", ""));
            FileWriter tempWriter = new FileWriter(input);
            tempWriter.write(file.toString());
            tempWriter.close();
            long vehicleNumber = 1;
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(input);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("vehicle");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node node = nList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    try {
                        tempId = Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent());
                    } catch (NumberFormatException exception) {
                        System.out.println("Невернаый id для vehicle № " + vehicleNumber + ".");
                    }
                    tempName = element.getElementsByTagName("name").item(0).getTextContent();
                    NodeList tempNodeListCoord = element.getElementsByTagName("coordinates");
                    Element elementCoord = (Element) tempNodeListCoord.item(0);
                    try {
                        tempX = Integer.parseInt(elementCoord.getElementsByTagName("coordinateX").item(0).getTextContent());
                        if (tempX <= -385) {
                            throw new NumberFormatException();
                        }
                    } catch (NumberFormatException exception) {
                        System.out.println("Неверная координата X для vehicle № " + vehicleNumber + ".");
                    }
                    try {
                        tempY = Integer.parseInt(elementCoord.getElementsByTagName("coordinateY").item(0).getTextContent());
                    } catch (NumberFormatException exception) {
                        System.out.println("Неверная координата Y для vehicle № " + vehicleNumber + ".");
                    }
                    try {
                        tempCreationDate = LocalDate.parse(element.getElementsByTagName("creationDate").item(0).getTextContent());
                    } catch (DateTimeParseException exception) {
                        System.out.println("Неверная дата создания для vehicle № " + vehicleNumber + ".");
                    }
                    try {
                        tempEnginePower = Float.parseFloat(element.getElementsByTagName("enginePower").item(0).getTextContent());
                        if (tempEnginePower <= 0) {
                            throw new NumberFormatException();
                        }
                    } catch (NumberFormatException exception) {
                        System.out.println("Неверная мощность двигателя для vehicle № " + vehicleNumber + ".");
                        tempEnginePower = 0;
                    }
                    try {
                        tempFuelConsumption = Long.parseLong(element.getElementsByTagName("fuelConsumption").item(0).getTextContent());
                        if (tempFuelConsumption <= 0) {
                            throw new NumberFormatException();
                        }
                    } catch (NumberFormatException exception) {
                        System.out.println("Wrong fuelConsumption in vehicle number " + vehicleNumber + ".");
                        tempFuelConsumption = 0;
                    }
                    if (!element.getElementsByTagName("distanceTravelled").item(0).getTextContent().equals("")) {
                        try {
                            tempDistanceTravelled = Integer.parseInt(element.getElementsByTagName("distanceTravelled").item(0).getTextContent());
                            if (tempDistanceTravelled <= 0) {
                                throw new NumberFormatException();
                            }
                        } catch (DateTimeParseException exception) {
                            System.out.println("Неверное пройденное расстояние для vehicle № " + vehicleNumber + ".");
                        }
                    }
                    tempFuelType = FuelType.valueOf(element.getElementsByTagName("fuelType").item(0).getTextContent());
                    vehicleNumber += 1;
                }
                if (tempId == null || tempName == null || tempY == null || tempX == 0 || tempCreationDate == null || tempEnginePower == 0 || tempFuelConsumption == 0 || tempDistanceTravelled == 0 || tempFuelType == null) {
                    System.out.println("Vehicle не может быть добавлен. Данные неверны.");
                } else if (incorrectNames.containsKey(vehicleNumber)) {
                    Vehicle vehicle = new Vehicle(tempId, incorrectNames.get(vehicleNumber), new Coordinates(tempX, tempY), tempCreationDate, tempEnginePower, tempFuelConsumption, tempDistanceTravelled, tempFuelType);
                    linkedHashSet.add(vehicle);
                } else {
                    Vehicle vehicle = new Vehicle(tempId, tempName, new Coordinates(tempX, tempY), tempCreationDate, tempEnginePower, tempFuelConsumption, tempDistanceTravelled, tempFuelType);
                    linkedHashSet.add(vehicle);
                }
            }
            System.out.println("Коллекция успешно загружена.");
        } catch (FactoryConfigurationError | ParserConfigurationException | IOException | SAXException exception) {
            System.out.println("Что-то пошло не так. Пожалуйста, исправьте файл и повторите попытку. (" + exception.getMessage() + ")");
        }

        return linkedHashSet;
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
        } catch (IllegalArgumentException ex) {
            System.out.println("Системная переменная с данным файлом не найдена.");
        }
    }
}

