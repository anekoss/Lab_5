package data;

/**
 * X-Y coordinates.
 */
public class Coordinates {
    private double x; //Значение поля должно быть больше -385
    private Integer y; //Поле не может быть null

    public Coordinates(double x, Integer y) {
        if (x > -385) {
            this.x = x;
        }
        if (y != null) {
            this.y = y;
        }
    }
//
//    @Override
//    public boolean equals(Coordinates coordinates) {
//        if(x==coordinates.getX()&&y.equals(coordinates.getY())){
//            return true;}
//        return false;
//    }

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
