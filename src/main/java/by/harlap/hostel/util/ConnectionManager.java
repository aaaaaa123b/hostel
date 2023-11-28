package by.harlap.hostel.util;

import by.harlap.hostel.repository.impl.UserRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
    final String url = "jdbc:postgresql://localhost:5432/postgres";
    final String username = "postgres";
    final String password = "4021";

    static {
        try {
            Class.forName("org.postgresql.Driver");
            logger.info("Драйвер 'org.postgresql.Driver' успешно загружен!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Драйвер для подключения к БД не был найден!", e);
        }
    }

    public Connection getConnection() {
        Connection connection;

        try {
            connection = DriverManager.getConnection(url, username, password);
            logger.info("Соединение успешно установлено!");
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось установить соединение с БД: ", e);
        }

        return connection;
    }

}
