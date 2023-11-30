package by.harlap.hostel.controller;

import by.harlap.hostel.model.Apartment;
import by.harlap.hostel.repository.ApartmentRepository;
import by.harlap.hostel.repository.impl.ApartmentRepositoryImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/reservationServlet")
public class ReservationController extends HttpServlet {

    private ApartmentRepository apartmentRepository;

    @Override
    public void init() {
        this.apartmentRepository = new ApartmentRepositoryImpl();
    }


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        String reservationIdStr = request.getParameter("apartmentId");

        if (action != null && reservationIdStr != null) {
            long hostelId = Long.parseLong(reservationIdStr);

            Apartment apartment = apartmentRepository.findById((int) hostelId);
            request.setAttribute("apartment", apartment);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/book_page.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        String apartmentId = request.getParameter("apartmentId");

        if ("Accept".equals(action) && apartmentId != null) {
            Apartment apartment = apartmentRepository.findById(Integer.parseInt(apartmentId));
            request.setAttribute("apartment", apartment);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/book_page.jsp");
        dispatcher.forward(request, response);
    }


}
