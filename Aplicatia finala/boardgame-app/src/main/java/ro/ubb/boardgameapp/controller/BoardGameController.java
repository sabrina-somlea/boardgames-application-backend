package ro.ubb.boardgameapp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.boardgameapp.converter.BoardGameConverter;
import ro.ubb.boardgameapp.dto.BoardGameDto;
import ro.ubb.boardgameapp.model.BoardGame;
import ro.ubb.boardgameapp.service.BoardGameService;

import java.util.List;
import java.util.Set;
import java.util.UUID;

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

    @PutMapping("/boardgames/{id}")
    BoardGameDto updateBoardGame(@PathVariable UUID id, @RequestBody BoardGameDto boardGameDto) {
        return boardGameConverter.convertEntityToDto(
                boardGameService.updateBoardGame(id,
                        boardGameConverter.convertDtoToEntity(boardGameDto))
        );
    }

    @DeleteMapping("/boardgames/{id}")
    ResponseEntity<?> deleteBoardGameById(@PathVariable UUID id) {
        boardGameService.deleteBoardGame(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/boardgames/search")
    public ResponseEntity<Set<BoardGame>> searchGamesByName(@RequestParam("name") String name) {
        Set<BoardGame> boardGames = boardGameService.searchBoardGames(name);

        if (boardGames.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(boardGames, HttpStatus.OK);
    }
}

