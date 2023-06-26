package ro.ubb.boardgameapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BoardGameWrapper {
    @JsonProperty("games")
    List<BoardGame> gameList;
}
