package by.harlap.hostel.repository.impl;

import by.harlap.hostel.dto.UserDto;
import by.harlap.hostel.enumerations.Role;
import by.harlap.hostel.exception.NoSuchEntityException;
import by.harlap.hostel.repository.UserRepository;
import by.harlap.hostel.util.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Override
    public List<UserDto> findAll() {

        Connection connection = connectionPool.getConnection();
        String query = "SELECT * FROM users";
        List<UserDto> users = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                final UserDto user = new UserDto();
                user.setLogin(rs.getString("login"));
                user.setUser_id(rs.getInt("user_id"));
                user.setPassword(rs.getString("password"));
                user.setRoles(Role.valueOf(rs.getString("role")));
                user.setSale(rs.getDouble("sale"));
                user.setBlock(rs.getBoolean("block"));
                user.setOrder_number(rs.getInt("order_number"));
                users.add(user);
            }
            return users;

        } catch (SQLException e) {
            throw new RuntimeException("Error processing SQL query", e);
        } finally {
            connectionPool.closeConnection(connection);
        }
    }


    public UserDto getUserByUsername(String login) {
        Connection connection = connectionPool.getConnection();
        String query = "SELECT * FROM users WHERE login = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, login);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                UserDto user = new UserDto();
                user.setUser_id(rs.getInt("user_id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setRoles(Role.valueOf(rs.getString("role")));
                user.setSale(rs.getDouble("sale"));
                user.setBlock(rs.getBoolean("block"));
                user.setOrder_number(rs.getInt("order_number"));
                return user;
            } else {
                throw new RuntimeException();

            }

        } catch (SQLException e) {
            throw new RuntimeException("Error processing SQL query", e);
        } finally {
            connectionPool.closeConnection(connection);
        }
    }


    @Override
    public UserDto addUser(String login, String password) {

        Connection connection = connectionPool.getConnection();
        final String query = "INSERT INTO users (login,password, role,block,sale,order_number) VALUES (?,?,?,?,?,?) RETURNING user_id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            Role role = Role.USER;
            double sale = 0.0;
            boolean block = false;
            int order_number = 0;
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, String.valueOf(role));
            preparedStatement.setBoolean(4, block);
            preparedStatement.setDouble(5, sale);
            preparedStatement.setInt(6, order_number);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    long id = resultSet.getLong("user_id");
                    final UserDto user = new UserDto();
                    user.setUser_id((int) id);
                    user.setPassword(password);
                    user.setLogin(login);
                    user.setRoles(role);
                    user.setBlock(block);
                    user.setSale(sale);
                    user.setOrder_number(order_number);
                    return user;
                } else {
                    logger.warn("Failed to add user");
                    return null;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query", e);
        } finally {
            connectionPool.closeConnection(connection);
        }
    }

    public UserDto updateUser(int userId, UserDto user) {

        Connection connection = connectionPool.getConnection();
        final String query = "UPDATE users SET login=?, password=?, role=?, block=?, sale=?, order_number=? WHERE user_id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, String.valueOf(user.getRoles()));
            preparedStatement.setBoolean(4, user.isBlock());
            preparedStatement.setDouble(5, user.getSale());
            preparedStatement.setInt(6, user.getOrder_number());
            preparedStatement.setInt(7, userId);

            int result = preparedStatement.executeUpdate();
            if (result == 0) {
                String message = "Пользователь с id %d не найден.".formatted(userId);
                throw new NoSuchEntityException(message);
            }

            return user;

        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query", e);
        } finally {
            connectionPool.closeConnection(connection);
        }
    }

    public UserDto findUserById(int user_id) {
        Connection connection = connectionPool.getConnection();
        String query = "SELECT * FROM users WHERE user_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, user_id);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                UserDto user = new UserDto();
                user.setUser_id(rs.getInt("user_id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setRoles(Role.valueOf(rs.getString("role")));
                user.setSale(rs.getDouble("sale"));
                user.setBlock(rs.getBoolean("block"));
                user.setOrder_number(rs.getInt("order_number"));
                return user;
            } else {
                throw new RuntimeException();

            }

        } catch (SQLException e) {
            throw new RuntimeException("Error processing SQL query", e);
        } finally {
            connectionPool.closeConnection(connection);
        }
    }

}
