package ro.ubb.boardgameapp.converter;

import org.springframework.stereotype.Component;
import ro.ubb.boardgameapp.dto.BoardGameDto;
import ro.ubb.boardgameapp.dto.UserDto;
import ro.ubb.boardgameapp.model.BoardGame;

@Component
public class BoardGameConverter extends BaseConverter<BoardGame, BoardGameDto> {
    @Override
    public BoardGame convertDtoToEntity(BoardGameDto dto) {
        BoardGame boardgame = BoardGame.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .yearPublished(dto.getYearPublished())
                .minPlayers(dto.getMinPlayers())
                .maxPlayers(dto.getMaxPlayers())
                .minPlayTime(dto.getMinPlayTime())
                .maxPlayTime(dto.getMaxPlayTime())
                .build();
        boardgame.setId(dto.getId());
        return boardgame;
    }

    @Override
    public BoardGameDto convertEntityToDto(BoardGame boardGame) {
        BoardGameDto boardGameDto = BoardGameDto.builder()
                .name(boardGame.getName())
                .description(boardGame.getDescription())
                .yearPublished(boardGame.getYearPublished())
                .minPlayers(boardGame.getMinPlayers())
                .maxPlayers(boardGame.getMaxPlayers())
                .minPlayTime(boardGame.getMinPlayTime())
                .maxPlayTime(boardGame.getMaxPlayTime())
                .build();
        boardGameDto.setId(boardGame.getId());
        return boardGameDto;
    }
}
