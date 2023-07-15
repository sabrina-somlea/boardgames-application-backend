package ro.ubb.boardgameapp.service;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.stereotype.Service;
import ro.ubb.boardgameapp.model.BoardGame;
import ro.ubb.boardgameapp.model.BoardGameWrapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
@Service
public class BoardGameApiService {
    private String API_URL = "https://api.boardgameatlas.com/api/search?name=%s&client_id=%s";
    private static final String client_key = "lkdjLvFi2P";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<BoardGame> searchBoardGames(String searchBoardGame) {
        if (searchBoardGame.contains(" ")) {
            String correctSearchBoard = searchBoardGame.replace(" ", "%20");
            String apiUrl = String.format(API_URL, correctSearchBoard, client_key);
            try {
                URL url = new URL(apiUrl);
                //ObjectReader objectReader = objectMapper.readerFor(new TypeReference<List<BoardGame>>() {}).withRootName("games");
                BoardGameWrapper boardGameWrapper = objectMapper.readValue(url, BoardGameWrapper.class);
                return boardGameWrapper.getGameList();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ArrayList<>();
        } else {
            String apiUrl = String.format(API_URL, searchBoardGame, client_key);
            System.out.println(apiUrl);

            try {
                URL url = new URL(apiUrl);
                //ObjectReader objectReader = objectMapper.readerFor(new TypeReference<List<BoardGame>>() {}).withRootName("games");
                BoardGameWrapper boardGameWrapper = objectMapper.readValue(url, BoardGameWrapper.class);
                return boardGameWrapper.getGameList();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ArrayList<>();
        }
    }
}
