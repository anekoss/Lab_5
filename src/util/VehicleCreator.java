package util;

import data.Coordinates;
import data.FuelType;
import data.Vehicle;
import exceptions.IncorrectFieldException;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class is responsible for the creation of the new Dragon object.
 */
public class VehicleCreator {
    Scanner scanner = new Scanner(System.in);

    /**
     * Creates new dragon.
     *
     * @return new dragon
     */
    public Vehicle createElement() {
        String name; //Поле не может быть null, Строка не может быть пустой
        //Поле не может быть null, Значение этого поля должно генерироваться автоматически
        float enginePower; //Значение поля должно быть больше 0
        long fuelConsumption; //Значение поля должно быть больше 0
        Integer distanceTravelled; //Поле может быть null, Значение поля должно быть больше 0
        FuelType fuelType; //Поле может быть null
        double x;
        Integer y;
        Vehicle vehicle;
        name = checkName();
        enginePower = checkEnginePower();
        fuelConsumption = checkFuelConsumption();
        x = checkX();
        y = checkY();
        //if (checkInDistanceTravelled()) {
            distanceTravelled = checkDistanceTravelled();
            //if (checkInFuelType()) {
                fuelType = checkFuelType();
                vehicle = new Vehicle(name,
                        new Coordinates(x, y),
                        enginePower,
                        fuelConsumption,
                        distanceTravelled,
                        fuelType);

//            } else {
//                vehicle = new Vehicle(name,
//                        new Coordinates(x, y),
//                        enginePower,
//                        fuelConsumption,
//                        distanceTravelled);
//            }
//        } else {
//            if (checkInFuelType()) {
//                fuelType = checkFuelType();
//                vehicle = new Vehicle(name,
//                        new Coordinates(x, y),
//                        enginePower,
//                        fuelConsumption,
//                        fuelType);
//            } else {
//                vehicle = new Vehicle(name,
//                        new Coordinates(x, y),
//                        enginePower,
//                        fuelConsumption);
//            }
//
        return vehicle;
    }

    /**
     * Reads the line from the console.
     *
     * @return line from the console
     */
    public String read() {
        String line;
        try {
            line = scanner.nextLine().trim();
        } catch (NoSuchElementException ex) {
            System.out.println("Завершение работы программы.");
            System.exit(0);
            line = null;
        }
        if (line.equals("")) {
            line = null;
        }
        return line;
    }

    public String checkName()  {
        FieldChecker<String> fieldChecker = str -> {
            if (str == null) {
                throw new IncorrectFieldException();
            }
            return str;
        };
        return readAndCheckField("имя", fieldChecker);
    }

    public <T> T readAndCheckField(String FieldName, FieldChecker<T> checker) {
        T temp;
        while (true) {
            System.out.println("Введите " + FieldName);
            try {
                temp = checker.check(read());
                System.out.println();
            } catch (NumberFormatException | IncorrectFieldException ex) {
                System.out.println("Неверный формат данных. Повторите ввод.");
                System.out.println();
                continue;}
            return temp;
        }
    }


    public float checkEnginePower() {
        FieldChecker<Float> fieldChecker = str -> {
            if (str == null) {
                throw new IncorrectFieldException();
            }
            float result = Float.parseFloat(str);
            if (result <= 0) {
                throw new NumberFormatException();
            }
            return result;
        };
        return readAndCheckField("мощность двигателя", fieldChecker);
    }

    public long checkFuelConsumption() throws NumberFormatException {
        FieldChecker<Long> fieldChecker = str -> {
            long result = Long.parseLong(str);
            if (result <= 0) {
                throw new NumberFormatException();
            }
            return result;
        };
        return readAndCheckField("расход топлива", fieldChecker);
    }


    public FuelType checkFuelType() {
            for (FuelType pos : FuelType.values()) {
                System.out.println(pos.toString());
        }
        FieldChecker<FuelType> fieldChecker = str -> {
                boolean checkFuelType=false;
            if (str == null) {
                return null;
            }
            else{
            for (FuelType pos : FuelType.values()) {
                if (str.toUpperCase().equals(pos.name())) {
                    checkFuelType = true;
                    break;
                }
            }
            if(!checkFuelType){
                throw new IncorrectFieldException();
            }
            else {
                return FuelType.valueOf(str.toUpperCase());
            }
        }};
        return readAndCheckField("тип топлива", fieldChecker);
    }

    public Integer checkDistanceTravelled() throws NumberFormatException {
        FieldChecker<Integer> fieldChecker = str -> {
            if (str==null)
                return null;
            else{
            Integer result = Integer.parseInt(str);
            if (result <= 0) {
                throw new NumberFormatException();
            }
            if(str.equals("")){
                result= null;
            }
            return result;
        }};
        return readAndCheckField("пройденное расстояние", fieldChecker);
    }


    public double checkX()  {
        FieldChecker<Double> fieldChecker = str -> {
            if(str==null){
                throw new IncorrectFieldException();
            }
            double result = Double.parseDouble(str);
            if (result <= -385) {
                throw new NumberFormatException();
            }
            return result;
        };
        return readAndCheckField("координату x", fieldChecker);
    }

    public Integer checkY()  {
        FieldChecker<Integer> fieldChecker = Integer::parseInt;
        return readAndCheckField("координату y", fieldChecker);

    }

    public Vehicle createFromScript(String[] fields) {
        try {
            String name = fields[0];
            float enginePower = Float.parseFloat(fields[1]);
            long fuelConsumption = Long.parseLong(fields[2]);
            double x = Double.parseDouble(fields[3]);
            Integer y = Integer.parseInt(fields[4]);
            if (enginePower > 0 && fuelConsumption > 0 && x > -385&&fields[0]!=null&&fields[1]!=null&&fields[2]!=null&&fields[3]!=null&&fields[4]!=null) {
                if (fields[5].equals("") && fields[6].equals("")) {
                    return new Vehicle(name, new Coordinates(x, y), enginePower, fuelConsumption);
                } else if (fields[5].equals("")) {
                    FuelType fuelType = FuelType.valueOf(fields[6].toUpperCase());
                    return new Vehicle(name, new Coordinates(x, y), enginePower, fuelConsumption, fuelType);
                } else if (fields[6].equals("")) {
                    Integer distanceTravelled = Integer.parseInt(fields[5]);
                    if (distanceTravelled > 0) {
                        return new Vehicle(name, new Coordinates(x, y), enginePower, fuelConsumption, distanceTravelled);
                    }
                } else {
                    Integer distanceTravelled = Integer.parseInt(fields[5]);
                    FuelType fuelType = FuelType.valueOf(fields[6].toUpperCase());
                    return new Vehicle(name, new Coordinates(x, y), enginePower, fuelConsumption, distanceTravelled, fuelType);
                }
            }
            else return null;
        } catch (IllegalArgumentException ex) {
            return null;
        }
        return null;
    }
}
