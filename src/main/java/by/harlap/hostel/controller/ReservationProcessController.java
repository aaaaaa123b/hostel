package by.harlap.hostel.controller;

import by.harlap.hostel.dto.ReservationDto;
import by.harlap.hostel.dto.UserDto;
import by.harlap.hostel.enumerations.Role;
import by.harlap.hostel.repository.AcceptApplicationRepository;
import by.harlap.hostel.repository.ApplicationRepository;
import by.harlap.hostel.repository.UserRepository;
import by.harlap.hostel.repository.impl.AcceptApplicationRepositoryImpl;
import by.harlap.hostel.repository.impl.UserRepositoryImpl;
import by.harlap.hostel.service.UserService;
import by.harlap.hostel.service.impl.UserServiceImpl;
import by.harlap.hostel.repository.impl.ApplicationRepositoryImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


@WebServlet(urlPatterns = "/processReservation")
public class ReservationProcessController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ReservationProcessController.class);
    private ApplicationRepository applicationRepository;
    private AcceptApplicationRepository acceptApplicationRepository;
    private UserService userService;
    private UserRepository userRepository;

    @Override
    public void init() {
        this.applicationRepository = new ApplicationRepositoryImpl();
        this.acceptApplicationRepository = new AcceptApplicationRepositoryImpl();
        this.userRepository = new UserRepositoryImpl();
        this.userService = new UserServiceImpl();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        Role adminRoles = (Role) session.getAttribute("roles");

        if (adminRoles != null && adminRoles.equals(Role.ADMIN)) {
            String action = request.getParameter("action");
            String reservationIdStr = request.getParameter("reservationId");

            if (action != null && reservationIdStr != null) {
                long reservationId = Long.parseLong(reservationIdStr);

                if ("Accept".equals(action)) {
                    acceptReservation((int) reservationId);
                } else if ("Reject".equals(action)) {
                    rejectReservation(reservationId);
                }
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin");
            dispatcher.forward(request, response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/error.jsp");
            dispatcher.forward(request, response);
        }

    }


    private void acceptReservation(int reservationId) {
        ReservationDto reservation = applicationRepository.findById(reservationId);
        acceptApplicationRepository.addReservation(reservation.getType(), reservation.getRoom_number(), reservation.getUser_id(), reservation.getHostel_id(), reservation.getHostel_name(), reservation.getApplication_type());
        applicationRepository.deleteById((long) reservationId);

        int user_id = reservation.getUser_id();
        int count = userService.calculateOrderNumber(user_id);
        UserDto user = userRepository.findUserById(user_id);
        user.setOrder_number(count);
        userRepository.updateUser(user_id, user);

        logger.info("Reservation accepted: " + reservationId);
    }

    private void rejectReservation(long reservationId) {
        applicationRepository.deleteById(reservationId);

        logger.info("Reservation rejected: " + reservationId);
    }

}


