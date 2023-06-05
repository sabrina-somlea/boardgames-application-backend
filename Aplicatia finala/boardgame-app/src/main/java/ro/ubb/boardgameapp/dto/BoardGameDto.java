package ro.ubb.boardgameapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString(callSuper = true)
public class BoardGameDto extends BaseDto {
    @NotBlank(message = "Please enter a valid name")
    private String name;
    @NotBlank(message = "Please enter a description")
    private String description;
    @NotNull(message = "Please enter a year")
    private int yearPublished;
    @NotNull(message = "Please enter a number of players")
    private int minPlayers;
    @NotNull(message = "Please enter a number of players")
    private int maxPlayers;
    @NotNull(message = "Please enter the minimum play time")
    private int minPlayTime;
    @NotNull(message = "Please enter the maximum play time")
    private int maxPlayTime;
}
