package ro.ubb.boardgameapp.repository;

import ro.ubb.boardgameapp.model.BoardGame;


import java.util.List;
import java.util.UUID;

public interface BoardGameRepository extends GenericRepository <BoardGame, UUID> {
    List<BoardGame> findByNameContainingIgnoreCase(String name);
}
