package ro.ubb.boardgameapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.boardgameapp.model.BoardGame;
import ro.ubb.boardgameapp.model.User;
import ro.ubb.boardgameapp.repository.BoardGameRepository;
import ro.ubb.boardgameapp.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BoardGameServiceImpl implements BoardGameService {
    @Autowired
    private BoardGameRepository boardGameRepository;
    @Autowired
    private UserRepository userRepository;

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
        Set<BoardGame> filteredApiBoardGames = result.stream().filter(
                        res -> res.getName() != null && !res.getName().isEmpty()
                                && res.getDescription() != null && !res.getDescription().isEmpty()
                                && res.getYearPublished() != 0
                                && res.getMinPlayers() != 0
                                && res.getMaxPlayers() != 0
                                && res.getMinPlayTime() != 0
                                && res.getMaxPlayTime() != 0)
                .collect(Collectors.toSet());
//        filteredApiBoardGames.forEach(res -> {
//            String correctDescription = res.getDescription().replaceAll("<[^>]+>", "");
//            res.setDescription(correctDescription);
//        });

        return filteredApiBoardGames;
    }
    @Override
    public Set<BoardGame> getAllBoardGamesByUsername(String username) {
        Optional<User> userOptional = userRepository.findOptionalByUsername(username);
        User user = userOptional.get();
        return user.getBoardGames();
    }

    @Override
    @Transactional
    public void deleteBoardGameFromUserCollection(String username, UUID boardgame_id) {
        Optional<User> userOptional = userRepository.findOptionalByUsername(username);
        User user = userOptional.get();
        BoardGame boardGame = boardGameRepository.findById(boardgame_id).get();
        user.getBoardGames().remove(boardGame);
        userRepository.save(user);
//        UUID userId = user.getId();
//        boardGameRepository.removeBoardGameFromUserCollection(userId, boardgame_id);

    }
}
