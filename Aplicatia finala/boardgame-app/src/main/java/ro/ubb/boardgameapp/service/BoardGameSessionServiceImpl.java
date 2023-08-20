package ro.ubb.boardgameapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.boardgameapp.model.BoardGame;
import ro.ubb.boardgameapp.model.BoardGameSession;
import ro.ubb.boardgameapp.model.User;
import ro.ubb.boardgameapp.repository.BoardGameRepository;
import ro.ubb.boardgameapp.repository.BoardGameSessionRepository;
import ro.ubb.boardgameapp.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BoardGameSessionServiceImpl implements BoardGameSessionService {

    @Autowired
    private BoardGameSessionRepository boardGameSessionRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public BoardGameSession saveBoardGameSession(BoardGameSession boardGameSession) {
        BoardGameSession saveBoardGameSession = boardGameSessionRepository.save(boardGameSession);
        return saveBoardGameSession;
    }

    @Override
    public BoardGameSession updateBoardGameSession(UUID id, BoardGameSession boardGameSession) {
        BoardGameSession updateBoardGameSession = boardGameSessionRepository.findById(id).orElseThrow();
        updateBoardGameSession.setSessionDate(boardGameSession.getSessionDate());
        updateBoardGameSession.setBoardGame(boardGameSession.getBoardGame());
        updateBoardGameSession.setPlayers(boardGameSession.getPlayers());
        updateBoardGameSession.setWinner(boardGameSession.getWinner());
        boardGameSessionRepository.save(updateBoardGameSession);
        return updateBoardGameSession;
    }

    @Override
    public void deleteBoardGameSession(UUID id) {
        boardGameSessionRepository.deleteById(id);

    }

    @Override
    public Set<BoardGameSession> getAllBoardGamesSessionsByUsername(String username) {
        Optional<User> userOptional = userRepository.findOptionalByUsername(username);
        User user = userOptional.get();
        return user.getUsersSessions();
    }

    @Override
    public Set<BoardGameSession> getAllBoardGamesSessionsWonByUsername(String username) {
        Optional<User> userOptional = userRepository.findOptionalByUsername(username);
        User user = userOptional.get();
        return user.getWonSessions();
    }

    @Override
    public Map<BoardGame, Long> getTop3PlayedGamesByUsername(String username) {
        Set<BoardGameSession> sessions = getAllBoardGamesSessionsByUsername(username);

        Map<BoardGame, Long> gameToPlayCount = sessions.stream()
                .collect(Collectors.groupingBy(BoardGameSession::getBoardGame, Collectors.counting()));

        Map<BoardGame, Long> sortedGameToPlayCount = gameToPlayCount.entrySet().stream()
                .sorted(Map.Entry.<BoardGame, Long>comparingByValue().reversed())
                .limit(3)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        return sortedGameToPlayCount;
    }

    @Override
    public Long countWonSessionsByUserId(UUID userId) {
        return boardGameSessionRepository.countByWinnerId(userId);
    }

    @Override
    public Long countPlayedSessionsByUserId(UUID userId) {
        return boardGameSessionRepository.countByPlayersId(userId);
    }

    @Override
    public Long countBoardGamesForUser(UUID userId) {
        return userRepository.countByBoardGamesId(userId);
    }
}
