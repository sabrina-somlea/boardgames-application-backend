package ro.ubb.boardgameapp.repository;

import ro.ubb.boardgameapp.model.User;

import java.util.UUID;

public interface UserRepository extends GenericRepository <User, UUID>{
    User findByUsername(String username);
}
