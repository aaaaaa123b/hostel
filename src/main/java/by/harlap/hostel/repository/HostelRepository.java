package by.harlap.hostel.repository;

import by.harlap.hostel.model.Hostel;

import java.util.List;

public interface HostelRepository {
    Hostel findById(int id);
    List<Hostel> findAll();

    Hostel findByName(String hostel_name);
}
