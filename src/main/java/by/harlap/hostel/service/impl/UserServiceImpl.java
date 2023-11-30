package by.harlap.hostel.service.impl;

import by.harlap.hostel.dto.ReservationDto;
import by.harlap.hostel.repository.AcceptApplicationRepository;
import by.harlap.hostel.repository.impl.AcceptApplicationRepositoryImpl;
import by.harlap.hostel.service.UserService;


import java.util.List;

public class UserServiceImpl implements UserService {
    private final AcceptApplicationRepository applicationRepository;

    public UserServiceImpl() {
        this.applicationRepository = new AcceptApplicationRepositoryImpl();
    }

    public int calculateOrderNumber(int userId) {
        List<ReservationDto> users = applicationRepository.findByUserId(userId);
        return users.size();
    }
}
