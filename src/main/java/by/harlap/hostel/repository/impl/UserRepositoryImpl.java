package by.harlap.hostel.repository.impl;

import by.harlap.hostel.enumerations.Role;
import by.harlap.hostel.model.User;
import by.harlap.hostel.util.ConnectionManager;
import by.harlap.hostel.repository.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private final ConnectionManager connectionManager;

    public UserRepositoryImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public List<User> findAll() {

        final Connection connection = connectionManager.getConnection();
        String query = "SELECT * FROM users";
        List<User> users = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                final User user = new User();
                user.setLogin(rs.getString("login"));
                user.setId(rs.getInt("user_id"));
                user.setPassword(rs.getString("password"));
                user.setRoles(Role.valueOf(rs.getString("role")));
                users.add(user);
            }
            return users;

        } catch (SQLException e) {
            throw new RuntimeException("Error processing SQL query", e);
        }
    }


    public User getUserByUsername(String login) {
        final Connection connection = connectionManager.getConnection();
        String query = "SELECT * FROM users WHERE login = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, login);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setRoles(Role.valueOf(rs.getString("role")));
                return user;
            } else {
                throw new RuntimeException();

            }

        } catch (SQLException e) {
            throw new RuntimeException("Error processing SQL query", e);
        }
    }


    @Override
    public User addUser(String login, String password) {

        Connection connection = connectionManager.getConnection();
        final String query = "INSERT INTO users (login,password, role) VALUES (?,?,?) RETURNING user_id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            Role role = Role.USER;
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, String.valueOf(role));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    long id = resultSet.getLong("user_id");
                    final User user = new User();
                    user.setId((int) id);
                    user.setPassword(password);
                    user.setLogin(login);
                    user.setRoles(role);
                    return user;
                } else {
                    System.out.println("Failed to add user.");
                    return null;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query", e);
        }
    }
}
