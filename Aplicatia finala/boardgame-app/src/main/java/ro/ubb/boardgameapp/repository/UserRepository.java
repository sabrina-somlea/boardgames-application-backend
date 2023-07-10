package ro.ubb.boardgameapp.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.ubb.boardgameapp.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends GenericRepository <User, UUID>{
    User findByUsername(String username);
    Optional<User> findOptionalByUsername(String username);
    Optional<User> findIdByUsername(String username);
}
