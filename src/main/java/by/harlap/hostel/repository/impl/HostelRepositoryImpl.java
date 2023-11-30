package by.harlap.hostel.repository.impl;

import by.harlap.hostel.exception.NoSuchEntityException;
import by.harlap.hostel.model.Hostel;
import by.harlap.hostel.repository.HostelRepository;
import by.harlap.hostel.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HostelRepositoryImpl implements HostelRepository {

    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public Hostel findById(int id) {
        Connection connection = connectionPool.getConnection();
        String query = "SELECT * FROM hostels WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                final Hostel hostel = new Hostel();
                hostel.setId(rs.getInt("id"));
                hostel.setHostel_name(rs.getString("hostel_name"));
                hostel.setCapacity(rs.getInt("capacity"));
                hostel.setLocation(rs.getString("location"));

                return hostel;
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
    public Hostel findByName(String hostel_name) {
        Connection connection = connectionPool.getConnection();
        String query = "SELECT * FROM hostels WHERE hostel_name = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, hostel_name);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                final Hostel hostel = new Hostel();
                hostel.setId(rs.getInt("id"));
                hostel.setHostel_name(rs.getString("hostel_name"));
                hostel.setCapacity(rs.getInt("capacity"));
                hostel.setLocation(rs.getString("location"));

                return hostel;
            } else {
                String message = "Reservation with hostel_name %s not found.".formatted(hostel_name);
                throw new RuntimeException(message);

            }

        } catch (SQLException e) {
            throw new RuntimeException("Error processing SQL query", e);
        } finally {
            connectionPool.closeConnection(connection);
        }
    }

    @Override
    public List<Hostel> findAll() {
        Connection connection = connectionPool.getConnection();
        String query = "SELECT * FROM hostels";
        List<Hostel> hostels = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Hostel hostel = new Hostel();
                hostel.setId(rs.getInt("id"));
                hostel.setHostel_name(rs.getString("hostel_name"));
                hostel.setCapacity(rs.getInt("capacity"));
                hostel.setLocation(rs.getString("location"));

                hostels.add(hostel);
            }
            return hostels;

        } catch (SQLException e) {
            throw new RuntimeException("Error processing SQL query", e);
        } finally {
            connectionPool.closeConnection(connection);
        }
    }

}
