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
        return y <= r && x <= r && x >= 0 && y >= r;
    }
    private boolean isInSecondSector(){

        return y <= r && x <= r && x >= 0 && y >= r;
    }
    private boolean isInThirdSector(){

        return y <= r && x <= r && x >= 0 && y >= r;
    }
    private boolean isInFourthSector(){

        return y <= r && x <= r && x >= 0 && y >= r;
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
