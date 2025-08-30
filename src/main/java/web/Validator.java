package web;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;

public class Validator {
    private double x;
    private double y;
    private double r;

    private Set<String> availableX = new HashSet<>(Arrays.asList("-2.0", "-1.5", "-1.0", "-0.5", "0", "0.5", "1.0", "1.5", "2.0" ));
    private Set<String> availableR = new HashSet<>(Arrays.asList("1", "1.5", "2", "2.5", "3"));

    public Validator(){
    }
    public boolean isValid(HttpServletRequest request){
        this.x = Double.parseDouble(request.getParameter("x"));
        this.y = Double.parseDouble(request.getParameter("y"));
        this.r = Double.parseDouble(request.getParameter("r"));

        return isValidX() && isValidY() && isValidR();
    }
    private boolean isValidX(){
        return availableX.contains(this.x);
    }
    private boolean isValidY(){
        return (this.y >= -3) && (this.y <= 3);
    }
    private boolean isValidR(){
        return availableR.contains(this.r);
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return x;
    }
    public double getR() {
        return x;
    }
}
