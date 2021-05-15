package util;

import data.Coordinates;
import data.FuelType;
import data.Vehicle;

import java.time.LocalDate;
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
        LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
        float enginePower; //Значение поля должно быть больше 0
        long fuelConsumption; //Значение поля должно быть больше 0
        Integer distanceTravelled; //Поле может быть null, Значение поля должно быть больше 0
        FuelType fuelType; //Поле может быть null
        double x;
        creationDate = LocalDate.now();
        Integer y;
        Vehicle vehicle;
        name = checkName();
        enginePower = checkEnginePower();
        fuelConsumption = checkFuelConsumption();
        x = checkX();
        y = checkY();
        if (checkInDistanceTravelled()) {
            distanceTravelled = checkDistanceTravelled();
            if (checkInFuelType()) {
                fuelType = checkFuelType();
                vehicle = new Vehicle(name,
                        new Coordinates(x, y),
                        enginePower,
                        fuelConsumption,
                        distanceTravelled,
                        fuelType);
            } else {
                vehicle = new Vehicle(name,
                        new Coordinates(x, y),
                        enginePower,
                        fuelConsumption,
                        distanceTravelled);
            }
        } else {
            if (checkInFuelType()) {
                fuelType = checkFuelType();
                vehicle = new Vehicle(name,
                        new Coordinates(x, y),
                        enginePower,
                        fuelConsumption,
                        fuelType);
            } else {
                vehicle = new Vehicle(name,
                        new Coordinates(x, y),
                        enginePower,
                        fuelConsumption);
            }
        }
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

    public String checkName() throws NullPointerException {
        FieldChecker<String> fieldChecker = str -> {
            if (str == null) {
                throw new NullPointerException();
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
            } catch (NumberFormatException | NullPointerException ex) {
                System.out.println("Неверный формат данных. Повторите ввод.");
                System.out.println();
                continue;
            } catch (IllegalArgumentException ex) {
                System.out.println("Данные введены неверно. Повторите ввод.");
                System.out.println();
                continue;
            }
            return temp;
        }
    }


    public float checkEnginePower() throws NumberFormatException {
        FieldChecker<Float> fieldChecker = str -> {
            Float result = Float.parseFloat(str);
            if (result <= -385) {
                throw new NumberFormatException();
            }
            return result;
        };
        return readAndCheckField("мощность двигателя", fieldChecker);
    }

    public long checkFuelConsumption() throws NumberFormatException {
        FieldChecker<Long> fieldChecker = str -> {
            Long result = Long.parseLong(str);
            if (result <= 0) {
                throw new NumberFormatException();
            }
            return result;
        };
        return readAndCheckField("расход топлива", fieldChecker);
    }

    public FuelType checkFuelType() throws IllegalArgumentException {
        FieldChecker<FuelType> fieldChecker = str -> FuelType.valueOf(str.toUpperCase());
        StringBuilder FuelTypeConstants = new StringBuilder();
        for (FuelType fuelType : FuelType.values()) {
            FuelTypeConstants.append(fuelType.toString()).append("\n");
        }
        FuelTypeConstants.deleteCharAt(FuelTypeConstants.length() - 1);
        return readAndCheckField("тип топлива\n" + FuelTypeConstants.toString(), fieldChecker);
    }

    public Integer checkDistanceTravelled() throws NumberFormatException {
        FieldChecker<Integer> fieldChecker = str -> {
            Integer result = Integer.parseInt(str);
            if (result <= 0) {
                throw new NumberFormatException();
            }
            return result;
        };
        return readAndCheckField("пройденное расстояние", fieldChecker);
    }


    public double checkX() throws NumberFormatException {
        FieldChecker<Double> fieldChecker = str -> {
            Double result = Double.parseDouble(str);
            if (result <= 0) {
                throw new NumberFormatException();
            }
            return result;
        };
        return readAndCheckField("координату x", fieldChecker);
    }

    public Integer checkY() throws NumberFormatException {
        FieldChecker<Integer> fieldChecker = Integer::parseInt;
        return readAndCheckField("координату y", fieldChecker);


    }

    public boolean checkInDistanceTravelled() {
        boolean checkCorrectAnswer = true;
        boolean ans = true;
        while (checkCorrectAnswer) {
            System.out.println("Вы хотите ввести пройденное расстояние? (YES/NO)");
            String answer = scanner.nextLine().trim();
            System.out.println();
            if (answer.equalsIgnoreCase("YES")) {
                checkCorrectAnswer = false;
                ans = true;
            } else if (answer.equalsIgnoreCase("NO")) {
                checkCorrectAnswer = false;
                ans = false;
            }
        }
        return ans;
    }

    public boolean checkInFuelType() {
        boolean checkCorrectAnswer = true;
        boolean ans = true;
        while (checkCorrectAnswer) {
            System.out.println("Вы хотите ввести тип топлива? (YES/NO)");
            String answer = scanner.nextLine().trim();
            System.out.println();
            if (answer.equalsIgnoreCase("YES")) {
                checkCorrectAnswer = false;
                ans = true;
            } else if (answer.equalsIgnoreCase("NO")) {
                checkCorrectAnswer = false;
                ans = false;
            }
        }
        return ans;
    }

    public Vehicle createFromScript(String[] fields) {
        try {
            String name = fields[0];
            float enginePower = Float.parseFloat(fields[1]);
            long fuelConsumption = Long.parseLong(fields[2]);
            double x = Double.parseDouble(fields[3]);
            Integer y = Integer.parseInt(fields[4]);
            LocalDate creationDate = LocalDate.now();
            if (enginePower > 0 && fuelConsumption > 0 && x > -385) {
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
        } catch (IllegalArgumentException ex) {
            return null;
        }
        return null;
    }
}
