package by.harlap.hostel.controller;


import by.harlap.hostel.dto.UserDto;
import by.harlap.hostel.model.Hostel;
import by.harlap.hostel.repository.ApartmentRepository;
import by.harlap.hostel.repository.ApplicationRepository;
import by.harlap.hostel.repository.HostelRepository;
import by.harlap.hostel.repository.impl.ApartmentRepositoryImpl;
import by.harlap.hostel.repository.impl.HostelRepositoryImpl;
import by.harlap.hostel.repository.impl.UserRepositoryImpl;
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
    private HostelRepository hostelRepository;
    private ApplicationRepository applicationRepository;
    private UserRepository userRepository;
    private ApartmentRepository apartmentRepository;

    @Override
    public void init() {
        this.hostelRepository = new HostelRepositoryImpl();
        this.applicationRepository = new ApplicationRepositoryImpl();
        this.userRepository = new UserRepositoryImpl();
        this.apartmentRepository = new ApartmentRepositoryImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        String login = (String) session.getAttribute("login");
        UserDto user = userRepository.getUserByUsername(login);

        String action = request.getParameter("action");
        String reservationType = request.getParameter("reservationType");

        String type = request.getParameter("type");
        int room_number = Integer.parseInt(request.getParameter("apartment_number"));
        int room_id = Integer.parseInt(request.getParameter("apartment_id"));
        String hostel_name = apartmentRepository.findById(room_id).getHostel_name();
        Hostel hostel = hostelRepository.findByName(hostel_name);

        if (action != null) {
            applicationRepository.addReservation(user.getUser_id(), hostel.getId(), reservationType, room_number, hostel_name, type);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/apartments");
            dispatcher.forward(request, response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/error.jsp");
            dispatcher.forward(request, response);
        }
    }

}
