package ro.ubb.boardgameapp.service;

import ro.ubb.boardgameapp.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> getAllUsers();

    User saveUser(User user);

    User updateUser(UUID id, User user);

    void deleteUser(UUID id);
User findByUsername(String username);

}
