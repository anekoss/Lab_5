package data;

/**
 * X-Y coordinates.
 */
public class Coordinates {
    private double x; //Значение поля должно быть больше -385
    private Integer y; //Поле не может быть null

    public Coordinates() {
        x = 0;
        y = 0;
    }

    public Coordinates(double x, Integer y) {
        if (x > -385) {
            this.x = x;
        }
        if (y != null) {
            this.y = y;
        }
    }

    /**
     * @return X-coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * @return Y-coordinate.
     */
    public Integer getY() {
        return y;
    }

}
