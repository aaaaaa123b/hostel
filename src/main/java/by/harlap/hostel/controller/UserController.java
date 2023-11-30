package by.harlap.hostel.controller;

import by.harlap.hostel.dto.UserDto;
import by.harlap.hostel.enumerations.Role;
import by.harlap.hostel.repository.UserRepository;
import by.harlap.hostel.repository.impl.UserRepositoryImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/users")
public class UserController extends HttpServlet {
    private UserRepository userRepository;

    @Override
    public void init() {
        this.userRepository = new UserRepositoryImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Role adminRoles = (Role) session.getAttribute("roles");

        if (adminRoles != null && adminRoles.equals(Role.ADMIN)) {

            List<UserDto> users = userRepository.findAll();
            request.setAttribute("users", users);

            request.getRequestDispatcher("/WEB-INF/view/admin_users_page.jsp").forward(request, response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/error.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Role adminRoles = (Role) session.getAttribute("roles");

        if (adminRoles != null && adminRoles.equals(Role.ADMIN)) {
            String user_id = request.getParameter("userId");

            if (user_id != null) {
                int userId = Integer.parseInt(request.getParameter("userId"));
                UserDto userDto = userRepository.findUserById(userId);

                boolean block = Boolean.parseBoolean(request.getParameter("block"));
                double sale = Double.parseDouble(request.getParameter("sale"));

                userDto.setBlock(block);
                userDto.setSale(sale);

                userRepository.updateUser(userId, userDto);

            }
            List<UserDto> users = userRepository.findAll();
            request.setAttribute("users", users);

            request.getRequestDispatcher("/WEB-INF/view/admin_users_page.jsp").forward(request, response);

        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/error.jsp");
            dispatcher.forward(request, response);
        }

    }
}
