package ro.ubb.boardgameapp.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ubb.boardgameapp.dto.BoardGameDto;
import ro.ubb.boardgameapp.dto.BoardGameSessionDto;
import ro.ubb.boardgameapp.dto.UserDto;
import ro.ubb.boardgameapp.model.BoardGame;
import ro.ubb.boardgameapp.model.BoardGameSession;
import ro.ubb.boardgameapp.model.User;
import ro.ubb.boardgameapp.service.BoardGameService;
import ro.ubb.boardgameapp.service.UserService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BoardGameSessionConverter extends BaseConverter<BoardGameSession, BoardGameSessionDto> {
    @Autowired
    BoardGameConverter boardGameConverter;

    @Autowired
    UserConverter userConverter;

    @Autowired
    BoardGameService boardGameService;

    @Autowired
    UserService userService;

    @Override
    public BoardGameSession convertDtoToEntity(BoardGameSessionDto dto) {
//        BoardGame boardGame = boardGameConverter.convertDtoToEntity(dto.getBoardGame());
//        User winner = userConverter.convertDtoToEntity(dto.getWinner());
//        Set<User> players = dto.getPlayers().stream().map(a -> userConverter.convertDtoToEntity(a)).collect(Collectors.toSet());
//        BoardGameSession boardgameSession = BoardGameSession.builder()
//                .sessionDate(dto.getSessionDate())
//                .boardGame(boardGame)
//                .winner(winner)
//                .players(players)
//                .build();
//        boardgameSession.setId(dto.getId());
//        return boardgameSession;
        BoardGameSession entity = new BoardGameSession();
        entity.setSessionDate(dto.getSessionDate());

        BoardGame boardGame = boardGameService.findBoardGameById(dto.getBoardGame().getId());
        entity.setBoardGame(boardGame);

        User winner = userService.findUserById(dto.getWinner().getId());
        entity.setWinner(winner);

        Set<User> players = new HashSet<>();
        for (UserDto playerDto : dto.getPlayers()) {
            User player = userService.findUserById(playerDto.getId());
            players.add(player);
        }
        entity.setPlayers(players);

        return entity;
    }

    @Override
    public BoardGameSessionDto convertEntityToDto(BoardGameSession boardGameSession) {
        BoardGameDto boardGameDto = boardGameConverter.convertEntityToDto(boardGameSession.getBoardGame());
        UserDto winner = userConverter.convertEntityToDto(boardGameSession.getWinner());
        Set<UserDto> players = boardGameSession.getPlayers().stream().map(a -> userConverter.convertEntityToDto(a)).collect(Collectors.toSet());
        BoardGameSessionDto boardGameSessionDto = BoardGameSessionDto.builder()
                .sessionDate(boardGameSession.getSessionDate())
                .boardGame(boardGameDto)
                .winner(winner)
                .players(players)
                .build();
        boardGameSessionDto.setId(boardGameSession.getId());
        return boardGameSessionDto;

    }
}
