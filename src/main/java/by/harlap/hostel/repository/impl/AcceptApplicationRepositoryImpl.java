package by.harlap.hostel.repository.impl;

import by.harlap.hostel.dto.ReservationDto;
import by.harlap.hostel.exception.NoSuchEntityException;
import by.harlap.hostel.repository.AcceptApplicationRepository;
import by.harlap.hostel.util.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AcceptApplicationRepositoryImpl implements AcceptApplicationRepository {
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final Logger logger = LoggerFactory.getLogger(AcceptApplicationRepositoryImpl.class);


    @Override
    public ReservationDto findById(int id) {
        Connection connection = connectionPool.getConnection();
        String query = "SELECT * FROM accept_applications WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                final ReservationDto reservation = new ReservationDto();
                reservation.setId(rs.getInt("id"));
                reservation.setType(rs.getString("type"));
                reservation.setRoom_number(rs.getInt("room_number"));
                reservation.setUser_id(rs.getInt("user_id"));
                reservation.setHostel_id(rs.getInt("hostel_id"));
                reservation.setHostel_name(rs.getString("hostel_name"));
                reservation.setApplication_type(rs.getString("application_type"));


                return reservation;
            } else {
                String message = "Reservation with id %d not found.".formatted(id);
                throw new NoSuchEntityException(message);

            }

        } catch (SQLException e) {
            throw new RuntimeException("Error processing SQL query", e);
        } finally {
            connectionPool.closeConnection(connection);
        }
    }

    @Override
    public List<ReservationDto> findAll() {
        Connection connection = connectionPool.getConnection();
        String query = "SELECT * FROM accept_applications";
        List<ReservationDto> reservations = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                ReservationDto reservation = new ReservationDto();
                reservation.setId(rs.getInt("id"));
                reservation.setType(rs.getString("type"));
                reservation.setRoom_number(rs.getInt("room_number"));
                reservation.setHostel_name(rs.getString("hostel_name"));
                reservation.setApplication_type(rs.getString("application_type"));

                reservations.add(reservation);
            }

            return reservations;

        } catch (SQLException e) {
            throw new RuntimeException("Error processing SQL query", e);
        } finally {
            connectionPool.closeConnection(connection);
        }
    }


    @Override
    public ReservationDto addReservation(String type, int room_number, int user_id, int hostel_id, String hostel_name, String applicationType) {

        Connection connection = connectionPool.getConnection();
        final String query = "INSERT INTO accept_applications (user_id,hostel_id,type, room_number,hostel_name,application_type) VALUES (?, ?,?,?,?,?) RETURNING id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, hostel_id);
            preparedStatement.setString(3, type);
            preparedStatement.setInt(4, room_number);
            preparedStatement.setString(5, hostel_name);
            preparedStatement.setString(6, applicationType);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    final ReservationDto reservation = new ReservationDto();
                    reservation.setId((int) id);
                    reservation.setType(type);
                    reservation.setRoom_number(room_number);
                    reservation.setUser_id(user_id);
                    reservation.setHostel_id(hostel_id);

                    return reservation;
                } else {
                    logger.warn("Failed to add reservation");
                    return null;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query", e);
        } finally {
            connectionPool.closeConnection(connection);
        }
    }


    @Override
    public void deleteById(Long id) {
        Connection connection = connectionPool.getConnection();
        final String query = "DELETE FROM accept_applications WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error processing SQL query", e);
        }
    }

    @Override
    public List<ReservationDto> findByUserId(int user_id) {
        Connection connection = connectionPool.getConnection();
        String query = "SELECT * FROM accept_applications WHERE user_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, user_id);

            List<ReservationDto> reservations = new ArrayList<>();
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                final ReservationDto reservation = new ReservationDto();
                reservation.setId(rs.getInt("id"));
                reservation.setType(rs.getString("type"));
                reservation.setRoom_number(rs.getInt("room_number"));
                reservation.setUser_id(rs.getInt("user_id"));
                reservation.setHostel_id(rs.getInt("hostel_id"));
                reservation.setHostel_name(rs.getString("hostel_name"));
                reservation.setApplication_type(rs.getString("application_type"));
                reservations.add(reservation);
            }
            return reservations;

        } catch (SQLException e) {
            throw new RuntimeException("Error processing SQL query", e);
        } finally {
            connectionPool.closeConnection(connection);
        }
    }


}
