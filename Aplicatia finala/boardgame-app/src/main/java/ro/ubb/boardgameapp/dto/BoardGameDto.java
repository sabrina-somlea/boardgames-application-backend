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
    @NotNull(message = "Please enter a valid name")
    private String name;
    @NotNull(message = "Please enter a description")
    private String description;
    @NotNull(message = "Please enter a year")
    private int year_published;
    @NotNull(message = "Please enter a number of players")
    private int min_players;
    @NotNull(message = "Please enter a number of players")
    private int max_players;
    @NotNull(message = "Please enter the minimum play time")
    private int min_playtime;
    @NotNull(message = "Please enter the maximum play time")
    private int max_playtime;
    private String image_url;
}
