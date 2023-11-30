package by.harlap.hostel.repository.impl;

import by.harlap.hostel.model.Apartment;
import by.harlap.hostel.repository.ApartmentRepository;
import by.harlap.hostel.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApartmentRepositoryImpl implements ApartmentRepository {
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public Apartment findById(int id) {
        Connection connection = connectionPool.getConnection();
        String query = "SELECT * FROM apartments WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                final Apartment apartment = new Apartment();
                apartment.setId(rs.getInt("id"));
                apartment.setHostel_name(rs.getString("hostel_name"));
                apartment.setRoom_number(rs.getInt("room_number"));
                apartment.setType(rs.getString("type"));

                return apartment;
            } else {
                String message = "Apartment with id %d not found.".formatted(id);
                throw new RuntimeException(message);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error processing SQL query", e);
        } finally {
            connectionPool.closeConnection(connection);
        }
    }

    @Override
    public List<Apartment> findAll() {
        Connection connection = connectionPool.getConnection();
        String query = "SELECT * FROM apartments";
        List<Apartment> apartmentList = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Apartment apartment = new Apartment();
                apartment.setId(rs.getInt("id"));
                apartment.setHostel_name(rs.getString("hostel_name"));
                apartment.setRoom_number(rs.getInt("room_number"));
                apartment.setType(rs.getString("type"));

                apartmentList.add(apartment);
            }
            return apartmentList;

        } catch (SQLException e) {
            throw new RuntimeException("Error processing SQL query", e);
        } finally {
            connectionPool.closeConnection(connection);
        }
    }
}
