package by.harlap.hostel.repository;

import by.harlap.hostel.model.User;

import java.util.List;

public interface UserRepository {

    User getUserByUsername(String login);

    User addUser(String login, String password);
    List<User> findAll();
}
