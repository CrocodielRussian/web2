package web.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.RequestDispatcher;

import web.PointsList;
import web.models.PointModel;

import java.io.IOException;
import java.util.logging.Logger;
import com.google.gson.Gson;
import java.util.Map;
import java.util.HashMap;

@WebServlet(name = "AreaCheckServlet", value = "/area_check-servlet")
public class AreaCheckServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Double x = Double.parseDouble(request.getParameter("X"));
            Double y = Double.parseDouble(request.getParameter("Y"));
            Double r = Double.parseDouble(request.getParameter("R"));
            PointModel point = new PointModel(x, y, r);

            HttpSession session = request.getSession();
            PointsList bean = (PointsList) session.getAttribute("bean");
            if (bean == null) {
                bean = new PointsList();
                session.setAttribute("bean", bean);
            }
            bean.addPoint(point);

            String action = request.getParameter("action");
            if ("submitForm".equals(action)) {
                request.setAttribute("X", x);
                request.setAttribute("Y", y);
                request.setAttribute("R", r);
                request.setAttribute("result", point.isInArea());

                RequestDispatcher dispatcher = request.getRequestDispatcher("./result.jsp");
                dispatcher.forward(request, response);

            } else if ("checkPoint".equals(action)) {
                Gson gson = new Gson();

                Map<String, Object> json = new HashMap<>();
                json.put("x", x);
                json.put("y", y);
                json.put("r", r);
                json.put("result", point.isInArea());

                String msg = gson.toJson(json);

                response.setContentType("application/json");
                response.getWriter().write(msg);
            }
        } catch (Exception e) {
            request.getRequestDispatcher("./index.jsp").forward(request, response);
        }
    }
}