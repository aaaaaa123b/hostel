package by.harlap.hostel.repository;

import by.harlap.hostel.model.Apartment;

import java.util.List;

public interface ApartmentRepository {
    Apartment findById(int id);

    List<Apartment> findAll();
}
