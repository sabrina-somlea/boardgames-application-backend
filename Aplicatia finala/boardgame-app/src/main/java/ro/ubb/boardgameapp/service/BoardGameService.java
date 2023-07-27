package ro.ubb.boardgameapp.service;

import ro.ubb.boardgameapp.model.BoardGame;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface BoardGameService {
    List<BoardGame> getAllBoardGames();


    BoardGame saveBoardGame( BoardGame boardGame);

    BoardGame updateBoardGame(UUID id, BoardGame boardGame);

    void deleteBoardGame(UUID id);

    Set<BoardGame> searchBoardGames (String searchBoardGame);

    Set<BoardGame> getAllBoardGamesByUsername(String username);

    void deleteBoardGameFromUserCollection (String username, UUID boardgame_id);

    BoardGame findBoardGameById (UUID id);
}
