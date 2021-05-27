package data;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

/**
 * This class contains the description of the elements stored in the collection.
 */
public class Vehicle implements Comparable<Vehicle> {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private final String name; //Поле не может быть null, Строка не может быть пустой
    private final Coordinates coordinates; //Поле не может быть null
    private final LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private final float enginePower; //Значение поля должно быть больше 0
    private final long fuelConsumption; //Значение поля должно быть больше 0
    private Integer distanceTravelled; //Поле может быть null, Значение поля должно быть больше 0
    private FuelType fuelType; //Поле может быть null

    public boolean vehicleHave(Vehicle vehicle) {
        return name.equals(vehicle.name) && coordinates.getX() == vehicle.coordinates.getX() && coordinates.getY().equals(vehicle.coordinates.getY()) && enginePower == vehicle.enginePower && fuelConsumption == vehicle.fuelConsumption&&fuelType==vehicle.fuelType&& Objects.equals(distanceTravelled, vehicle.distanceTravelled);
    }

    public Vehicle(Integer id, String name, Coordinates coordinates, float enginePower, long fuelConsumption, Integer distanceTravelled, FuelType fuelType) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.enginePower = enginePower;
        this.fuelConsumption = fuelConsumption;
        this.distanceTravelled = distanceTravelled;
        this.fuelType = fuelType;
    }


    public Vehicle(Integer id, String name, Coordinates coordinates, float enginePower, long fuelConsumption, FuelType fuelType) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.enginePower = enginePower;
        this.fuelConsumption = fuelConsumption;
        this.fuelType = fuelType;
    }

    public Vehicle(Integer id, String name, Coordinates coordinates, float enginePower, long fuelConsumption) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.enginePower = enginePower;
        this.fuelConsumption = fuelConsumption;
    }

    public Vehicle(Integer id, String name, Coordinates coordinates, float enginePower, long fuelConsumption, Integer distanceTravelled) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.enginePower = enginePower;
        this.fuelConsumption = fuelConsumption;
        this.distanceTravelled = distanceTravelled;
    }

    @Override
    public int compareTo(Vehicle vehicle) {
        if (this.getEnginePower() == vehicle.getEnginePower()) {
            return getName().compareTo(vehicle.getName());
        }
        return Float.compare(this.getEnginePower(), vehicle.getEnginePower());
    }

    public Vehicle(String name, Coordinates coordinates, float enginePower, long fuelConsumption, Integer distanceTravelled, FuelType fuelType) {
        this.name = name;
        this.coordinates = coordinates;
        this.enginePower = enginePower;
        this.creationDate = LocalDate.now();
        this.fuelConsumption = fuelConsumption;
        this.distanceTravelled = distanceTravelled;
        this.fuelType = fuelType;
    }

    public Vehicle(String name, Coordinates coordinates, float enginePower, long fuelConsumption, Integer distanceTravelled) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.enginePower = enginePower;
        this.fuelConsumption = fuelConsumption;
        this.distanceTravelled = distanceTravelled;
    }

    public Vehicle(String name, Coordinates coordinates, float enginePower, long fuelConsumption, FuelType fuelType) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.enginePower = enginePower;
        this.fuelConsumption = fuelConsumption;
        this.fuelType = fuelType;
    }

    public void setId(Integer setValue) {
        id = setValue;
    }

    public Vehicle(String name, Coordinates coordinates, float enginePower, long fuelConsumption) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.enginePower = enginePower;
        this.fuelConsumption = fuelConsumption;
    }

    /**
     * @return id of the vehicle.
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return Fuel Type of the vehicle.
     */
    public FuelType getFuelType() {
        return fuelType;
    }

    /**
     * @return Distance Travelled of the vehicle.
     */
    public Integer getDistanceTravelled() {
        return distanceTravelled;
    }

    /**
     * @return Fuel Consumption of the vehicle.
     */
    public long getFuelConsumption() {
        return fuelConsumption;
    }

    /**
     * @return Engine Power of the vehicle.
     */
    public float getEnginePower() {
        return enginePower;
    }

    /**
     * @return CreationDate of the vehicle.
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * @return Coordinates of the vehicle.
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * @return Name of the vehicle.
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String S = "";
        S += "Vehicle\n";
        S += "\tId: " + id + "\n";
        S += "\tДата создания: " + creationDate + "\n";
        S += "\tИмя: " + name + "\n";
        S += "\tМощность двигателя: " + enginePower + "\n";
        S += "\tРасход топлива: " + fuelConsumption + "\n";
        if (distanceTravelled != null) {
            S += "\tПройденное расстояние: " + distanceTravelled + "\n";
        }
        if (fuelType != null) {
            S += "\tТип топлива: " + fuelType + "\n";
        }
        S += "\tКоординаты:\n";
        S += "\t\tx = " + coordinates.getX() + "\n";
        S += "\t\ty = " + coordinates.getY();
        return S;
    }

    public static final Comparator<Vehicle> COMPARE_BY_DISTANCE_TRAVELLED = Comparator.comparingInt(Vehicle::getDistanceTravelled);

    public static final Comparator<Vehicle> COMPARE_BY_ID = Comparator.comparingInt(Vehicle::getId);
}