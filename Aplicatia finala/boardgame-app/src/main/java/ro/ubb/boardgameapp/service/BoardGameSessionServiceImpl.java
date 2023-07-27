package ro.ubb.boardgameapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.boardgameapp.model.BoardGame;
import ro.ubb.boardgameapp.model.BoardGameSession;
import ro.ubb.boardgameapp.model.User;
import ro.ubb.boardgameapp.repository.BoardGameRepository;
import ro.ubb.boardgameapp.repository.BoardGameSessionRepository;
import ro.ubb.boardgameapp.repository.UserRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

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
}
