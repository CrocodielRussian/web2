package web.models;

import java.lang.*;
import java.io.Serializable;

public class PointModel implements Serializable{
    private static final long serialVersionUID = 1;

    private final double x;
    private final double y;
    private final double r;

    public PointModel(double x, double y, double r){
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }


    public boolean isInArea(){
        return isInFirstSector() || isInSecondSector() || isInThirdSector() || isInFourthSector();
    }
    private boolean isInFirstSector(){
        return x >= 0 && y >= 0 && (x + y <= r);
    }
    private boolean isInSecondSector(){
        return y <= r && x <= 0 && x >= -r && y >= 0;
    }
    private boolean isInThirdSector(){

        return x <= 0 && y <= 0 && ((Math.pow(x, 2) + Math.pow(y, 2)) <= Math.pow(r/2, 2));
    }
    private boolean isInFourthSector(){

        return false;
    }

    @Override
    public String toString(){
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", r=" + r +
                ", isInArea=" + String.valueOf(this.isInArea()) +
                '}';
    }
}
