package web;

import web.models.PointModel;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import jakarta.ejb.Stateful;
import jakarta.enterprise.context.SessionScoped;

@Stateful
@SessionScoped
public class PointsList implements Serializable{
    private static final long serialVersionUID = 1L;

    private final List<PointModel> points = new ArrayList<>();

    public void addPoint(PointModel point) {
        points.add(point);
    }

    public List<PointModel> getPoints() {
        return points;
    }

}
