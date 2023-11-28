package by.harlap.hostel.controller;


import by.harlap.hostel.dto.ReservationDto;
import by.harlap.hostel.enumerations.Role;
import by.harlap.hostel.repository.ApplicationRepository;
import by.harlap.hostel.util.ConnectionManager;
import by.harlap.hostel.repository.impl.ApplicationRepositoryImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;


@WebServlet(urlPatterns = "/admin")
public class AdminController extends HttpServlet {
    ApplicationRepository applicationRepository;
    ConnectionManager connectionManager;

    @Override
    public void init() {
        this.connectionManager = new ConnectionManager();
        this.applicationRepository = new ApplicationRepositoryImpl(connectionManager);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Role adminRoles = (Role) session.getAttribute("roles");

        if (adminRoles != null && adminRoles.equals(Role.ADMIN)) {
            List<ReservationDto> reservationList = applicationRepository.findAll();

            request.setAttribute("reservations", reservationList);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/admin_applications.jsp");
            dispatcher.forward(request, response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/error.jsp");
            dispatcher.forward(request, response);
        }
    }


}
