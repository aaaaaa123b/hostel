package by.harlap.hostel.repository;

import by.harlap.hostel.dto.ReservationDto;

import java.util.List;

public interface ApplicationRepository {
    ReservationDto findById(int id);

    ReservationDto addReservation(int user_id, int hostel_id, String type, int numberOfSeats,String hostel_name,String applicationType) ;

    void deleteById(Long id);


    List<ReservationDto> findAll();
}
