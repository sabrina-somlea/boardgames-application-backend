package ro.ubb.boardgameapp.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UpdatePasswordDto {
    private String initialPassword;
    private String newPassword;
}
