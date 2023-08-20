package ro.ubb.boardgameapp.service;

import ro.ubb.boardgameapp.model.BoardGame;
import ro.ubb.boardgameapp.model.BoardGameSession;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface BoardGameSessionService {

    BoardGameSession saveBoardGameSession(BoardGameSession boardGameSession);

    BoardGameSession updateBoardGameSession(UUID id, BoardGameSession boardGameSession);

    void deleteBoardGameSession(UUID id);

    Set<BoardGameSession> getAllBoardGamesSessionsByUsername(String username);

    Set<BoardGameSession> getAllBoardGamesSessionsWonByUsername(String username);

    Map<BoardGame, Long> getTop3PlayedGamesByUsername(String username);

    Long countWonSessionsByUserId(UUID userId);

    Long countPlayedSessionsByUserId(UUID userId);

    Long countBoardGamesForUser(UUID userId);
}