package by.harlap.hostel.controller;

import by.harlap.hostel.enumerations.Role;
import by.harlap.hostel.model.Apartment;
import by.harlap.hostel.repository.ApartmentRepository;
import by.harlap.hostel.repository.impl.ApartmentRepositoryImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/apartments")
public class HostelController extends HttpServlet {
    private ApartmentRepository apartmentRepository;

    @Override
    public void init() {
        this.apartmentRepository = new ApartmentRepositoryImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Role adminRoles = (Role) session.getAttribute("roles");

        if (adminRoles != null && adminRoles.equals(Role.USER)) {

            List<Apartment> reservationList = apartmentRepository.findAll();
            request.setAttribute("hostelList", reservationList);

            request.getRequestDispatcher("/WEB-INF/view/user_page.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(request, response);
        }
    }

}
