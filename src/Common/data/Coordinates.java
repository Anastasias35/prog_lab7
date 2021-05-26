package Common.data;

import java.io.Serializable;
import java.util.Objects;

/**
 * X-Y координаты
 */
public class Coordinates implements Serializable {
    private  int x;
    private Double y;
    public Coordinates(int x, Double y){
        this.x=x;
        this.y=y;
    }

    /**
     * @return координата X
     */
    public int getX(){
        return x;
    }

    /**
     * @return координата Y
     */
    public Double getY(){
        return y;
    }
    public String coordinatesToCSV(){
        return String.valueOf(x) + " " + String.valueOf(y);
    }

   @Override
    public String toString() {
        return  "x=" + x + " " + "y=" + y + "\n" ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x &&
                Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
