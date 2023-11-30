package by.harlap.hostel.controller;

import by.harlap.hostel.dto.UserDto;
import by.harlap.hostel.repository.impl.UserRepositoryImpl;
import by.harlap.hostel.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/login", name = "AuthController")
public class AuthController extends HttpServlet {

    private UserRepository userRepository;

    @Override
    public void init() {
        this.userRepository = new UserRepositoryImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        final String reqLine = req.getReader().readLine();
        Map<String, String> fields = parseParameters(reqLine);

        final String login = fields.get("login");
        final String password = fields.get("password");
        UserDto user = userRepository.getUserByUsername(login);

        if (user.getPassword().equals(password)) {
            req.getSession().setAttribute("roles", user.getRoles());
            req.getSession().setAttribute("login", user.getLogin());
            resp.sendRedirect("/dashboard");
        } else {
            throw new RuntimeException();
        }

    }

    public static Map<String, String> parseParameters(String paramString) {
        Map<String, String> paramMap = new HashMap<>();

        if (paramString != null && !paramString.isEmpty()) {
            String[] pairs = paramString.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = keyValue[1];
                    paramMap.put(key, value);
                }
            }
        }

        return paramMap;
    }

}
