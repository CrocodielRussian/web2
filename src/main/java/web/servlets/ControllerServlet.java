package web.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name = "ControllerServlet", value = "/controller-servlet")
public class ControllerServlet extends HttpServlet{

    public void init(){

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException {
        generateResponse(request, response);
    }
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException {
        generateResponse(request, response);
    }

    public void generateResponse(HttpServletRequest
                                         request,
                                 HttpServletResponse response) throws IOException {

    }

}