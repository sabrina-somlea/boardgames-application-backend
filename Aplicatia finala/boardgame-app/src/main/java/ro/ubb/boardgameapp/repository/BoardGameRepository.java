package ro.ubb.boardgameapp.repository;

import ro.ubb.boardgameapp.model.BoardGame;


import java.util.UUID;

public interface BoardGameRepository extends GenericRepository <BoardGame, UUID> {
}
