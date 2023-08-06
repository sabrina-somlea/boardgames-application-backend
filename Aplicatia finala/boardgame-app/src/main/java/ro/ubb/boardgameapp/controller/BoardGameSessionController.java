package ro.ubb.boardgameapp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.boardgameapp.converter.BoardGameSessionConverter;
import ro.ubb.boardgameapp.dto.BoardGameDto;
import ro.ubb.boardgameapp.dto.BoardGameSessionDto;
import ro.ubb.boardgameapp.model.BoardGame;
import ro.ubb.boardgameapp.model.BoardGameSession;
import ro.ubb.boardgameapp.repository.BoardGameSessionRepository;
import ro.ubb.boardgameapp.service.BoardGameSessionService;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
//@CrossOrigin(origins ="*")
@CrossOrigin(origins = "http://localhost:4200/")
public class BoardGameSessionController {
    @Autowired
    private BoardGameSessionConverter boardGameSessionConverter;

    @Autowired
    private BoardGameSessionService boardGameSessionService;

    @Autowired
    private BoardGameSessionRepository boardGameSessionRepository;

    @GetMapping(value = "/{username}/viewBoardGamesSessions")
    Set<BoardGameSessionDto> getAllBoardGamesSessionByUsername(@PathVariable String username) {
        Set<BoardGameSession> allUserBoardGamesSession = boardGameSessionService.getAllBoardGamesSessionsByUsername(username);
        Set<BoardGameSessionDto> boardGameSessionDtos = boardGameSessionConverter.convertEntitiesToDtos(allUserBoardGamesSession);
        return boardGameSessionDtos;
    }

    @PostMapping("/addBoardGamesSessions")
    BoardGameSessionDto saveBoardGameSession(@Valid @RequestBody BoardGameSessionDto boardGameSessionDto) {
        BoardGameSession boardGameSession = boardGameSessionConverter.convertDtoToEntity(boardGameSessionDto);
        BoardGameSession savedBoardGameSession = boardGameSessionService.saveBoardGameSession(boardGameSession);
        return boardGameSessionConverter.convertEntityToDto(savedBoardGameSession);
    }

    @DeleteMapping("/session/{session_id}")public
    ResponseEntity<?> deleteBoardGameById(@PathVariable UUID session_id) {
        boardGameSessionService.deleteBoardGameSession(session_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping("/session/{session_id}")
    BoardGameSessionDto updateBoardGameSession (@PathVariable UUID session_id, @RequestBody BoardGameSessionDto boardGameSessionDto) {
        return boardGameSessionConverter.convertEntityToDto(
                boardGameSessionService.updateBoardGameSession(session_id,
                        boardGameSessionConverter.convertDtoToEntity(boardGameSessionDto))
        );
    }

}
