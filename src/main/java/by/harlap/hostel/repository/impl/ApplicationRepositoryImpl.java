package by.harlap.hostel.repository.impl;

import by.harlap.hostel.dto.ReservationDto;
import by.harlap.hostel.repository.ApplicationRepository;
import by.harlap.hostel.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationRepositoryImpl implements ApplicationRepository {

    private final ConnectionManager connectionManager;

    public ApplicationRepositoryImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public ReservationDto findById(int id) {
        final Connection connection = connectionManager.getConnection();
        String query = "SELECT * FROM applications WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                final ReservationDto reservation = new ReservationDto();
                reservation.setId(rs.getInt("id"));
                reservation.setType(rs.getString("type"));
                reservation.setNumberOfSeats(rs.getInt("number_of_seats"));
                reservation.setUser_id(rs.getInt("user_id"));
                reservation.setHostel_id(rs.getInt("hostel_id"));

                return reservation;
            } else {
                String message = "Reservation with id %d not found.".formatted(id);
                throw new RuntimeException(message);

            }

        } catch (SQLException e) {
            throw new RuntimeException("Error processing SQL query", e);
        }
    }

    @Override
    public List<ReservationDto> findAll() {
        final Connection connection = connectionManager.getConnection();
        String query = "SELECT * FROM applications";
        List<ReservationDto> reservations = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                ReservationDto reservation = new ReservationDto();
                reservation.setId(rs.getInt("id"));
                reservation.setType(rs.getString("type"));
                reservation.setNumberOfSeats(rs.getInt("number_of_seats"));
                reservation.setUser_id(rs.getInt("user_id"));
                reservation.setHostel_id(rs.getInt("hostel_id"));
                reservations.add(reservation);
            }

            return reservations;

        } catch (SQLException e) {
            throw new RuntimeException("Error processing SQL query", e);
        }
    }


    @Override
    public ReservationDto addReservation(int user_id, int hostel_id, String type, int numberOfSeats) {

        Connection connection = connectionManager.getConnection();
        final String query = "INSERT INTO applications (user_id,hostel_id, number_of_seats,type) VALUES (?, ?,?,?) RETURNING id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, hostel_id);
            preparedStatement.setInt(3, numberOfSeats);
            preparedStatement.setString(4, type);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    final ReservationDto reservation = new ReservationDto();
                    reservation.setId((int) id);
                    reservation.setType(type);
                    reservation.setNumberOfSeats(numberOfSeats);
                    reservation.setUser_id(user_id);
                    reservation.setHostel_id(hostel_id);
                    return reservation;
                } else {
                    System.out.println("Failed to add reservation.");
                    return null;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error executing SQL query", e);
        }
    }


    @Override
    public void deleteById(Long id) {
        Connection connection = connectionManager.getConnection();
        final String query = "DELETE FROM applications WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении SQL-запроса", e);
        }
    }


//    @Override
//    public Reservation updateReservation(int id, Reservation reservation) {
//        Connection connection = connectionManager.getConnection();
//
//        String query = "UPDATE applications SET type = ?, number_of_seats = ? WHERE id = ?";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, reservation.getType());
//            preparedStatement.setInt(2, reservation.getNumberOfSeats());
//            preparedStatement.setLong(3, id);
//
//            int result = preparedStatement.executeUpdate();
//
//            if (result == 0) {
//                String message = "Reservation with id %d not found.".formatted(id);
//                throw new RuntimeException(message);
//            }
//
//            reservation.setId(id);
//            return reservation;
//
//        } catch (SQLException e) {
//            throw new RuntimeException("Error processing SQL query", e);
//        }
//    }

}