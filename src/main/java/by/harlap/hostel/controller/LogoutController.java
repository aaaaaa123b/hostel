package by.harlap.hostel.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(urlPatterns = "/logout")
public class LogoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        final HttpSession session = request.getSession();

        session.removeAttribute("password");
        session.removeAttribute("login");
        session.removeAttribute("role");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/register.jsp");
        dispatcher.forward(request, response);

    }

}
