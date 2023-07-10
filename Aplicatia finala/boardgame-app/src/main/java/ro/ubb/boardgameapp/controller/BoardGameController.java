package ro.ubb.boardgameapp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.boardgameapp.converter.BoardGameConverter;
import ro.ubb.boardgameapp.converter.UserConverter;
import ro.ubb.boardgameapp.dto.BoardGameDto;
import ro.ubb.boardgameapp.dto.UserDto;
import ro.ubb.boardgameapp.model.BoardGame;
import ro.ubb.boardgameapp.model.User;
import ro.ubb.boardgameapp.repository.BoardGameRepository;
import ro.ubb.boardgameapp.repository.UserRepository;
import ro.ubb.boardgameapp.service.BoardGameService;
import ro.ubb.boardgameapp.service.UserService;

import java.util.List;
import java.util.Optional;
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

    @Autowired
    UserConverter userConverter;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardGameRepository boardGameRepository;

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

//        if (boardGames.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }

        return new ResponseEntity<>(boardGames, HttpStatus.OK);
    }

    @PostMapping(value = "/{username}/addToCollection")
    public ResponseEntity<String> addBoardGameToCollection(@PathVariable String username, @RequestBody BoardGameDto boardGameDto) {

        Optional<User> userOptional = userRepository.findOptionalByUsername(username);
//        Optional<BoardGame> gameOptional = boardGameRepository.findById(boardGameDto.getId());
        Optional<BoardGame> gameOptional = boardGameRepository.findByName(boardGameDto.getName());
        if (userOptional.isPresent() && gameOptional.isPresent()) {
            User user = userOptional.get();
            user.getBoardGames().add(gameOptional.get());
            userRepository.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }else if (gameOptional.isEmpty()){
            BoardGame savedGame = boardGameRepository.save(boardGameConverter.convertDtoToEntity(boardGameDto));
            userOptional.get().getBoardGames().add(savedGame);
            userRepository.save(userOptional.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/{username}/viewCollection")
    Set<BoardGameDto> getAllBoardGamesByUsername(@PathVariable String username){
        Set<BoardGame> allUserBoardGames = boardGameService.getAllBoardGamesByUsername(username);
        Set<BoardGameDto> boardGamesDtos = boardGameConverter.convertEntitiesToDtos(allUserBoardGames);
        return boardGamesDtos;
    }

    @DeleteMapping("/users/{username}/boardgames/{boardgame_id}")
    ResponseEntity<?> deleteBoardGameById(@PathVariable(value= "username") String username,@PathVariable(value= "boardgame_id") UUID boardgame_id) {
        boardGameService.deleteBoardGameFromUserCollection(username, boardgame_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    }



