package ro.ubb.boardgameapp.converter;

import org.springframework.stereotype.Component;
import ro.ubb.boardgameapp.dto.BoardGameDto;
import ro.ubb.boardgameapp.model.BoardGame;

@Component
public class BoardGameConverter extends BaseConverter<BoardGame, BoardGameDto> {
    @Override
    public BoardGame convertDtoToEntity(BoardGameDto dto) {
        BoardGame boardgame = BoardGame.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .yearPublished(dto.getYear_published())
                .minPlayers(dto.getMin_players())
                .maxPlayers(dto.getMax_players())
                .minPlayTime(dto.getMin_playtime())
                .maxPlayTime(dto.getMax_playtime())
                .imageUrl(dto.getImage_url())
                .build();
        boardgame.setId(dto.getId());
        return boardgame;
    }

    @Override
    public BoardGameDto convertEntityToDto(BoardGame boardGame) {
        BoardGameDto boardGameDto = BoardGameDto.builder()
                .name(boardGame.getName())
                .description(boardGame.getDescription())
                .year_published(boardGame.getYearPublished())
                .min_players(boardGame.getMinPlayers())
                .max_players(boardGame.getMaxPlayers())
                .min_playtime(boardGame.getMinPlayTime())
                .max_playtime(boardGame.getMaxPlayTime())
                .image_url(boardGame.getImageUrl())
                .build();
        boardGameDto.setId(boardGame.getId());
        return boardGameDto;
    }
}
