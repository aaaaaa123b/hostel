package by.harlap.hostel.repository;

import by.harlap.hostel.dto.ReservationDto;

import java.util.List;

public interface AcceptApplicationRepository {

    ReservationDto findById(int id);

    ReservationDto addReservation(String type, int numberOfSeats,int user_id,int hostel_id);

    void deleteById(Long id);

    ReservationDto updateReservation(int id, ReservationDto reservation);

    List<ReservationDto> findAll();
}
