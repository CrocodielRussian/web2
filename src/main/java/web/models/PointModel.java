package web.models;

import java.lang.*;
import java.io.Serializable;

public class PointModel implements Serializable{
    private static final long serialVersionUID = 1;

    private final int x;
    private final int y;
    private final int z;
    private final int r;

    PointModel(int x, int y, int z, int r){
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = r;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
    public int getR() {
        return r;
    }

    public boolean isInArea(){
        return true;
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
