package by.harlap.hostel.controller;

import by.harlap.hostel.model.Hostel;
import by.harlap.hostel.repository.impl.HostelRepositoryImpl;
import by.harlap.hostel.util.ConnectionManager;
import by.harlap.hostel.repository.HostelRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/reservationServlet")
public class ReservationController extends HttpServlet {

    HostelRepository hostelRepository;
    ConnectionManager connectionManager;

    @Override
    public void init() {
        this.connectionManager = new ConnectionManager();
        this.hostelRepository = new HostelRepositoryImpl(connectionManager);
    }


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        String reservationIdStr = request.getParameter("hostelId");

        if (action != null && reservationIdStr != null) {
            long hostelId = Long.parseLong(reservationIdStr);

            Hostel hostel = hostelRepository.findById((int) hostelId);
            request.setAttribute("reservation", hostel);

        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/book_page.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        String hostelIdStr = request.getParameter("hostelId");

        if (action != null && hostelIdStr != null) {
            long hostelId = Long.parseLong(hostelIdStr);

            if ("Accept".equals(action)) {
                Hostel hostel = hostelRepository.findById((int) hostelId);
                request.setAttribute("reservation", hostel);
            }
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/book_page.jsp");
        dispatcher.forward(request, response);
    }


}
