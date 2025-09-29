package web.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.Validator;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ControllerServlet.class.getName());
    private Validator checker;

    @Override
    public void init() {
        this.checker = new Validator();
        LOGGER.info("ControllerServlet initialized with Validator");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException{
        LOGGER.fine("Received POST request to /controller");

        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading POST request body", e);
            sendError(response, "Error reading request body: " + e.getMessage());
            return;
        }

        String body = requestBody.toString();
        LOGGER.fine("Request body: " + body);


        Map<String, String> params = parseQueryString(body);
        LOGGER.fine("Parsed parameters: " + params);

        String x = params.get("x");
        String y = params.get("y");
        String r = params.get("r");
        String typeRequest = params.get("action");

        if (x == null || y == null || r == null || typeRequest == null) {
            LOGGER.severe("Missing parameters: x=" + x + ", y=" + y + ", r=" + r + ", action=" + typeRequest);
            sendError(response, "Missing required parameters");
            return;
        }

        request.setAttribute("x", x);
        request.setAttribute("y", y);
        request.setAttribute("r", r);
        request.setAttribute("action", typeRequest);

        String queryString = "./areacheck?x=" + x + "&y=" + y + "&r=" + r + "&action=" + typeRequest;

        try {
            if (typeRequest.equals("checkPoint") || checker.isValid(request)) {
                LOGGER.info("Validation passed, redirecting to " + queryString);
                response.sendRedirect(queryString);
            } else {
                LOGGER.warning("Validation failed for request: " + queryString);
                sendError(response, "Invalid input parameters");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing POST request: " + queryString, e);
            sendError(response, "Server error: " + e.getMessage());
        }
    }
    private Map<String, String> parseQueryString(String query) {
        Map<String, String> params = new HashMap<>();
        if (query == null || query.trim().isEmpty()) {
            LOGGER.warning("Empty or null query string");
            return params;
        }

        try {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=", 2); // Split on first '=' only
                if (keyValue.length == 2) {
                    String key = java.net.URLDecoder.decode(keyValue[0], "UTF-8");
                    String value = java.net.URLDecoder.decode(keyValue[1], "UTF-8");
                    params.put(key, value);
                    LOGGER.fine("Parsed key-value: " + key + "=" + value);
                } else {
                    LOGGER.warning("Invalid key-value pair: " + pair);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error parsing query string: " + query, e);
        }
        return params;
    }

    private void sendError(HttpServletResponse response, String errorMessage) throws IOException {
        LOGGER.severe("Sending error response: " + errorMessage);
        Gson json = new Gson();
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("error", errorMessage);
        jsonResponse.put("status", "UNPROCESSABLE_ENTITY");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json.toJson(jsonResponse));
        response.setStatus(422);
    }
}