package by.harlap.hostel.controller;

import by.harlap.hostel.enumerations.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(urlPatterns = "/dashboard")
public class DashboardController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Role role = (Role) session.getAttribute("roles");
        moveToMenu(req,resp,role);

    }

    private void moveToMenu(final HttpServletRequest req,
                            final HttpServletResponse res,
                            final Role role)
            throws ServletException, IOException {

        if (role.equals(Role.ADMIN)) {

            req.getRequestDispatcher("/WEB-INF/view/admin_page.jsp").forward(req, res);

        } else if (role.equals(Role.USER)) {

            req.getRequestDispatcher("/hostels").forward(req, res);
        } else {

            req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, res);
        }
    }
}
