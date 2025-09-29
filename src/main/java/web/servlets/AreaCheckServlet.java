package web.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.RequestDispatcher;
import web.models.PointModel;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

@WebServlet("/areacheck")
public class AreaCheckServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(AreaCheckServlet.class.getName());

    @Override
    public void init() {
        LOGGER.info("AreaCheckServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.fine("Received GET request to /areacheck");
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String queryString = request.getQueryString();
        LOGGER.fine("Processing request with query: " + (queryString != null ? queryString : "none"));

        String sx = request.getParameter("x");
        String sy = request.getParameter("y");
        String sr = request.getParameter("r");
        String action = request.getParameter("action");


        LOGGER.fine(String.format("Validating request parameters: x=%s, y=%s, r=%s", sx, sy, sr));

        if (sx == null || sy == null || sr == null || action == null) {
            LOGGER.severe("One or more parameters are missing: x=" + sx + ", y=" + sy + ", r=" + sr);
            throw new IllegalArgumentException("One or more parameters are missing");
        }

        try {
            double x = Double.parseDouble(request.getParameter("x"));
            double y = Double.parseDouble(request.getParameter("y"));
            double r = Double.parseDouble(request.getParameter("r"));

            LOGGER.fine(String.format("Parsed parameters: x=%.2f, y=%.2f, r=%.2f", x, y, r));

            PointModel point = new PointModel(x, y, r);
            LOGGER.info("Created PointModel: " + point.toString());

            HttpSession session = request.getSession();
            List<PointModel> results = (List<PointModel>) session.getAttribute("results");
            if (results == null) {
                results = new ArrayList<>();
                LOGGER.info("Initialized new results list in session");
            }

            results.add(point);
            session.setAttribute("results", results);
            LOGGER.info("Added point to session results, total points: " + results.size());


            LOGGER.info("Action parameter: " + (action != null ? action : "none"));

            if ("submitForm".equals(action)) {
                LOGGER.info("Forwarding to jsp-page for action=submitForm");
                RequestDispatcher dispatcher = request.getRequestDispatcher("./result.jsp");
                dispatcher.forward(request, response);
            } else if ("checkPoint".equals(action)) {
                LOGGER.info("Preparing JSON response for action=checkPoint");
                Gson gson = new Gson();
                Map<String, Object> json = new HashMap<>();
                json.put("x", x);
                json.put("y", y);
                json.put("r", r);
                json.put("result", point.isInArea());

                String msg = gson.toJson(json);
                LOGGER.fine("JSON response: " + msg);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(msg);
            } else {
                LOGGER.warning("Unknown action parameter: " + action);
                request.getRequestDispatcher("./index.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Invalid parameter format in request: " + queryString, e);
            request.getRequestDispatcher("./index.jsp").forward(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error processing request: " + queryString, e);
            request.getRequestDispatcher("./index.jsp").forward(request, response);
        }
    }
}