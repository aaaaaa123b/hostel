package by.harlap.hostel.crud;

import by.harlap.hostel.dto.ReservationDto;
import by.harlap.hostel.enumerations.Role;
import by.harlap.hostel.repository.AcceptApplicationRepository;
import by.harlap.hostel.repository.ApplicationRepository;
import by.harlap.hostel.repository.impl.AcceptApplicationRepositoryImpl;
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


@WebServlet(urlPatterns = "/processReservation")
public class ReservationProcessController extends HttpServlet {
    ApplicationRepository applicationRepository;
    AcceptApplicationRepository acceptApplicationRepository;
    ConnectionManager connectionManager;

    @Override
    public void init() {
        this.connectionManager = new ConnectionManager();
        this.applicationRepository = new ApplicationRepositoryImpl(connectionManager);
        this.acceptApplicationRepository = new AcceptApplicationRepositoryImpl(connectionManager);
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
        acceptApplicationRepository.addReservation(reservation.getType(), reservation.getNumberOfSeats(), reservation.getUser_id(), reservation.getHostel_id());
        applicationRepository.deleteById((long) reservationId);
        System.out.println("Reservation accepted: " + reservationId);
    }

    private void rejectReservation(long reservationId) {
        applicationRepository.deleteById(reservationId);

        System.out.println("Reservation rejected: " + reservationId);
    }

}


