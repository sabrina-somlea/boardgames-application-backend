package ro.ubb.boardgameapp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.ubb.boardgameapp.model.BoardGame;
import ro.ubb.boardgameapp.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends GenericRepository <User, UUID>{
    User findByUsername(String username);

    Optional<User> findOptionalByUsername(String username);
    Optional<User> findIdByUsername(String username);

    User findUserById (UUID id);

    @Query(value = "SELECT u FROM User u WHERE " +
            "(LOWER(u.username) LIKE LOWER(CONCAT('%', :searchQuery, '%'))) OR " +
            "(LOWER(u.firstName) LIKE LOWER(CONCAT('%', :searchQuery, '%'))) OR " +
            "(LOWER(u.lastName) LIKE LOWER(CONCAT('%', :searchQuery, '%'))) OR " +
            "(LOWER(CONCAT(u.firstName, ' ', u.lastName)) LIKE LOWER(CONCAT('%', :searchQuery, '%'))) OR " +
            "(LOWER(CONCAT(u.lastName, ' ', u.firstName)) LIKE LOWER(CONCAT('%', :searchQuery, '%')))"
           )
    List<User> searchUsers(String searchQuery);

    @Query("SELECT bg FROM BoardGame bg " +
            "JOIN bg.boardGamesSession s " +
            "JOIN s.players p " +
            "WHERE p = :user " +
            "GROUP BY bg " +
            "ORDER BY COUNT(bg) DESC")
    List<BoardGame> findTop3PlayedGamesByUser(@Param("user") Optional<User> user);

    Long countByFriendsId(UUID friendId);


    @Query("SELECT COUNT(bg) FROM User u JOIN u.boardGames bg WHERE u.id = :userId")
    Long countByBoardGamesId(@Param("userId") UUID userId);


}
