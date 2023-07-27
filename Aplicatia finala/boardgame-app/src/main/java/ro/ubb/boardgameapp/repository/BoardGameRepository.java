package ro.ubb.boardgameapp.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.ubb.boardgameapp.model.BoardGame;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Transactional
public interface BoardGameRepository extends GenericRepository <BoardGame, UUID> {
    List<BoardGame> findByNameContainingIgnoreCase(String name);
    Optional<BoardGame> findByName(String name);
    Optional<BoardGame> findIdByName(String name);

   BoardGame findBoardGameById (UUID id);
    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.id = :userId AND :boardgame_Id MEMBER OF u.boardGames")
    void removeBoardGameFromUserCollection(@Param("userId") UUID userId, @Param("boardgame_Id") UUID boardgame_id);
}
