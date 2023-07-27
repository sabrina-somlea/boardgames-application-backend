package ro.ubb.boardgameapp.dto;

import lombok.*;
import ro.ubb.boardgameapp.model.BoardGame;
import ro.ubb.boardgameapp.model.User;

import java.util.Date;
import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString(callSuper = true)
public class BoardGameSessionDto  extends BaseDto {
    private Date sessionDate;
//    private BoardGameDto boardGame;
//    private UserDto winner;
//    private Set<UserDto> players;

    private BoardGameDto boardGame;
    private UserDto winner;
    private Set<UserDto> players;
}
