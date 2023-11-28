package by.harlap.hostel.repository.impl;

import by.harlap.hostel.model.Hostel;
import by.harlap.hostel.util.ConnectionManager;
import by.harlap.hostel.repository.HostelRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HostelRepositoryImpl implements HostelRepository {
    private final ConnectionManager connectionManager;

    public HostelRepositoryImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public Hostel findById(int id) {
        final Connection connection = connectionManager.getConnection();
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
                throw new RuntimeException(message);

            }

        } catch (SQLException e) {
            throw new RuntimeException("Error processing SQL query", e);
        }
    }

    @Override
    public List<Hostel> findAll() {
        final Connection connection = connectionManager.getConnection();
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
        }
    }

}
