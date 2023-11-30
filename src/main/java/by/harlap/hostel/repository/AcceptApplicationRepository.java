package by.harlap.hostel.repository;

import by.harlap.hostel.dto.ReservationDto;

import java.util.List;

public interface AcceptApplicationRepository {

    ReservationDto findById(int id);

    ReservationDto addReservation(String type, int room_number, int user_id, int hostel_id,String hostel_name,String applicationType);
    void deleteById(Long id);

    List<ReservationDto> findByUserId(int user_id);

    List<ReservationDto> findAll();
}
