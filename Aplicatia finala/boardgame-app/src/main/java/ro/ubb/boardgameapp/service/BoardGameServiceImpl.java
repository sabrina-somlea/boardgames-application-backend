package ro.ubb.boardgameapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.boardgameapp.model.BoardGame;
import ro.ubb.boardgameapp.repository.BoardGameRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class BoardGameServiceImpl implements BoardGameService{
    @Autowired
    private BoardGameRepository boardGameRepository;

    @Autowired
    private BoardGameApiService boardGameApiService;
    @Override
    public List<BoardGame> getAllBoardGames() {
        return boardGameRepository.findAll();
    }

    @Override
    public BoardGame saveBoardGame(BoardGame boardGame) {
        BoardGame saveBoardGame = boardGameRepository.save(boardGame);
        return saveBoardGame;
    }
    @Transactional
    @Override
    public BoardGame updateBoardGame(UUID id, BoardGame boardGame) {
        BoardGame updateBoardGame = boardGameRepository.findById(id).orElseThrow();
        updateBoardGame.setName(boardGame.getName());
        updateBoardGame.setDescription(boardGame.getDescription());
        updateBoardGame.setYearPublished(boardGame.getYearPublished());
        updateBoardGame.setMinPlayers(boardGame.getMinPlayers());
        updateBoardGame.setMaxPlayers(boardGame.getMaxPlayers());
        updateBoardGame.setMinPlayTime(boardGame.getMinPlayTime());
        updateBoardGame.setMaxPlayTime(boardGame.getMaxPlayTime());

        return updateBoardGame;
    }

    @Override
    public void deleteBoardGame(UUID id) {
        boardGameRepository.deleteById(id);

    }

    @Override
    public Set<BoardGame> searchBoardGames(String searchBoardGame) {

        List<BoardGame> databaseBoardGames = boardGameRepository.findByNameContainingIgnoreCase(searchBoardGame);
        List<BoardGame> apiBoardGames = boardGameApiService.searchBoardGames(searchBoardGame);
        Set<BoardGame> result = new HashSet<>(databaseBoardGames);
        result.addAll(apiBoardGames);

        return result;
    }
}
