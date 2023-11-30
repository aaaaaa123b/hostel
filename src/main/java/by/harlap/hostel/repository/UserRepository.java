package by.harlap.hostel.repository;

import by.harlap.hostel.dto.UserDto;

import java.util.List;

public interface UserRepository {

    UserDto getUserByUsername(String login);

    UserDto addUser(String login, String password);

    List<UserDto> findAll();

    UserDto updateUser(int userId, UserDto user);

    UserDto findUserById(int user_id);
}
