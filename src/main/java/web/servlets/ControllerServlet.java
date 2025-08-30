package web.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.Validator;
import com.google.gson.Gson;

import java.util.Map;
import java.util.HashMap;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name = "ControllerServlet", value = "/controller-servlet")
public class ControllerServlet extends HttpServlet{
    private Validator checker;

    public void init(){
        this.checker = new Validator();
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException {

        if(checker.isValid(request)){
            response.sendRedirect("./area_check-servlet?" + request.getQueryString());
        }else{
            sendError(response, "error");
        }
    }
    private void sendError(HttpServletResponse response, String errorMessage) throws IOException {
        Gson json = new Gson();
        Map<String, Object> jsonResponse = new HashMap<>() {{
            put("error", errorMessage);
            put("status", "UNPROCESSABLE_ENTITY");
        }};

        response.setContentType("application/json");
        response.getWriter().write(json.toJson(jsonResponse));
        response.setStatus(422);
    }



}