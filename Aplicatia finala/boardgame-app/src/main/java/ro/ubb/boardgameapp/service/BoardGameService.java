package ro.ubb.boardgameapp.service;

import ro.ubb.boardgameapp.model.BoardGame;

import java.util.List;
import java.util.UUID;

public interface BoardGameService {
    List<BoardGame> getAllBoardGames();

    BoardGame saveBoardGame( BoardGame boardGame);

    BoardGame updateBoardGame(UUID id, BoardGame boardGame);

    void deleteBoardGame(UUID id);
}
