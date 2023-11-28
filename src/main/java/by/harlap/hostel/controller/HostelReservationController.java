package by.harlap.hostel.controller;


import by.harlap.hostel.model.User;
import by.harlap.hostel.repository.ApplicationRepository;
import by.harlap.hostel.repository.HostelRepository;
import by.harlap.hostel.repository.impl.HostelRepositoryImpl;
import by.harlap.hostel.repository.impl.UserRepositoryImpl;
import by.harlap.hostel.util.ConnectionManager;
import by.harlap.hostel.repository.UserRepository;
import by.harlap.hostel.repository.impl.ApplicationRepositoryImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


@WebServlet(urlPatterns = "/hostelReservation")
public class HostelReservationController extends HttpServlet {
    HostelRepository hostelRepository;
    ConnectionManager connectionManager;
    ApplicationRepository applicationRepository;
    UserRepository userRepository;

    @Override
    public void init() {
        this.connectionManager = new ConnectionManager();
        this.hostelRepository = new HostelRepositoryImpl(connectionManager);
        this.applicationRepository = new ApplicationRepositoryImpl(connectionManager);
        this.userRepository = new UserRepositoryImpl(connectionManager);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        String login = (String) session.getAttribute("login");
        User user = userRepository.getUserByUsername(login);

        String action = request.getParameter("action");
        String reservationType = request.getParameter("reservationType");
        int numberOfSeats = Integer.parseInt(request.getParameter("numberOfSeats"));
        int hostelId = Integer.parseInt(request.getParameter("hostelId"));

        if (action != null) {
            applicationRepository.addReservation(user.getId(), hostelId, reservationType, numberOfSeats);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/hostels");
            dispatcher.forward(request, response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/error.jsp");
            dispatcher.forward(request, response);
        }
    }

}
