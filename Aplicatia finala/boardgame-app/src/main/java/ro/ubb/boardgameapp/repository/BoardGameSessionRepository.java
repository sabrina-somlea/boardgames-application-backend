package ro.ubb.boardgameapp.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ro.ubb.boardgameapp.model.BoardGameSession;


import java.util.UUID;

public interface BoardGameSessionRepository  extends GenericRepository <BoardGameSession, UUID>, JpaSpecificationExecutor<BoardGameSession> {

    Long countByWinnerId(UUID winnerId);

    Long countByPlayersId(UUID playerId);
}
