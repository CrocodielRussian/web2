package web;

import web.models.PointModel;


public class PointChecker {

    PointChecker(){

    }
    public boolean isInForm(PointModel point){
        int x = point.getX();
        int y = point.getY();
        int z = point.getZ();
        int r = point.getR();

        return isInFirstSector(x, y, z, r) || isInSecondSector(x, y, z, r) || isInThirdSector(x, y, z, r) || isInFourthSector(x, y, z, r);
    }
    private boolean isInFirstSector(int x, int y, int z, int r){

        return y <= r && x <= r && x >= 0 && y >= r;
    }
    private boolean isInSecondSector(int x, int y, int z, int r){

        return y <= r && x <= r && x >= 0 && y >= r;
//        return (true);
    }
    private boolean isInThirdSector(int x, int y, int z, int r){

        return y <= r && x <= r && x >= 0 && y >= r;
    }
    private boolean isInFourthSector(int x, int y, int z, int r){

        return y <= r && x <= r && x >= 0 && y >= r;
    }
}
