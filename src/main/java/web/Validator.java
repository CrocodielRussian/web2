package web;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Validator {
    private static final Logger LOGGER = Logger.getLogger(Validator.class.getName());
    private double x;
    private double y;
    private double r;

//    private final Set<String> availableX = new HashSet<>(Arrays.asList("-2.0", "-1.5", "-1.0", "-0.5", "0", "0.5", "1.0", "1.5", "2.0"));
//    private final Set<String> availableR = new HashSet<>(Arrays.asList("1", "1.5", "2", "2.5", "3"));

    public Validator() {
        LOGGER.info("Validator initialized");
    }

    public boolean isValid(HttpServletRequest request) {
        String sx = (String)request.getAttribute("x");
        String sy = (String)request.getAttribute("y");
        String sr = (String)request.getAttribute("r");

        LOGGER.fine(String.format("Validating request parameters: x=%s, y=%s, r=%s", sx, sy, sr));

        if (sx == null || sy == null || sr == null) {
            LOGGER.severe("One or more parameters are missing: x=" + sx + ", y=" + sy + ", r=" + sr);
            throw new IllegalArgumentException("One or more parameters are missing");
        }

        try {
            this.x = Double.parseDouble(sx);
            this.y = Double.parseDouble(sy);
            this.r = Double.parseDouble(sr);
            LOGGER.fine(String.format("Parsed parameters: x=%.2f, y=%.2f, r=%.2f", x, y, r));

            boolean valid = isValidX() && isValidY() && isValidR();
            if (valid) {
                LOGGER.info("Validation successful for x=" + x + ", y=" + y + ", r=" + r);
            } else {
                LOGGER.warning("Validation failed for x=" + x + ", y=" + y + ", r=" + r);
            }
            return valid;
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Invalid number format for parameters: x=" + sx + ", y=" + sy + ", r=" + sr, e);
            throw new IllegalArgumentException("Invalid number format for parameters", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error during validation: x=" + sx + ", y=" + sy + ", r=" + sr, e);
            throw new RuntimeException("Validation error", e);
        }
    }

    private boolean isValidX() {
        boolean valid = Math.abs(this.x) <= 3;
        LOGGER.fine("Validating x=" + String.valueOf(this.x) + ": " + (valid ? "valid" : "invalid"));
        return valid;
    }

    private boolean isValidY() {
        boolean valid = Math.abs(this.y) <= 2;
        LOGGER.fine("Validating y=" + String.valueOf(this.y) + ": " + (valid ? "valid" : "invalid"));
        return valid;
    }

    private boolean isValidR() {
//        boolean valid = availableR.contains(String.valueOf(this.r));
        boolean valid = this.r <= 3;
        LOGGER.fine("Validating r=" + String.valueOf(this.r) + ": " + (valid ? "valid" : "invalid"));
        return valid;
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
}