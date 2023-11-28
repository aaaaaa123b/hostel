package by.harlap.hostel.crud;

import by.harlap.hostel.enumerations.Role;
import by.harlap.hostel.model.Hostel;
import by.harlap.hostel.repository.HostelRepository;
import by.harlap.hostel.repository.impl.HostelRepositoryImpl;
import by.harlap.hostel.util.ConnectionManager;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/hostels")
public class HostelController extends HttpServlet {
    HostelRepository hostelRepository;
    ConnectionManager connectionManager;

    @Override
    public void init() {
        this.connectionManager = new ConnectionManager();
        this.hostelRepository = new HostelRepositoryImpl(connectionManager);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Role adminRoles = (Role) session.getAttribute("roles");

        if (adminRoles != null && adminRoles.equals(Role.USER)) {
            List<Hostel> reservationList = hostelRepository.findAll();

            request.setAttribute("hostelList", reservationList);

            request.getRequestDispatcher("/WEB-INF/view/user_page.jsp").forward(request, response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/error.jsp");
            dispatcher.forward(request, response);
        }
    }

}
