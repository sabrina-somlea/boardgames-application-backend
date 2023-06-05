package ro.ubb.boardgameapp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.ubb.boardgameapp.converter.BoardGameConverter;
import ro.ubb.boardgameapp.dto.BoardGameDto;
import ro.ubb.boardgameapp.dto.UserDto;
import ro.ubb.boardgameapp.model.BoardGame;
import ro.ubb.boardgameapp.model.User;
import ro.ubb.boardgameapp.service.BoardGameService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
//@CrossOrigin(origins ="*")
@CrossOrigin(origins = "http://localhost:4200/")
public class BoardGameController {
    @Autowired
    private BoardGameService boardGameService;

    @Autowired
    private BoardGameConverter boardGameConverter;

    @GetMapping("/boardgames")
//    @CrossOrigin(origins = "*", exposedHeaders = {"Access-Control-Allow-Origin"} )
    Set<BoardGameDto> getAllBoardGames() {
        List<BoardGame> allGames = boardGameService.getAllBoardGames();
        Set<BoardGameDto> boardGamesDtos = boardGameConverter.convertEntitiesToDtos(allGames);
        return boardGamesDtos;

    }
    @RequestMapping(value = "/boardgames", method = RequestMethod.POST)
    BoardGameDto saveBoardGame(@Valid @RequestBody BoardGameDto boardGameDto) {
        BoardGame boardGame = boardGameConverter.convertDtoToEntity(boardGameDto);
        BoardGame savedBoardGame = boardGameService.saveBoardGame(boardGame);
        return boardGameConverter.convertEntityToDto(savedBoardGame);
    }
}

