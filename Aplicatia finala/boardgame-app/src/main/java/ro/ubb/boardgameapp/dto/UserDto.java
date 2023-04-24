package ro.ubb.boardgameapp.dto;

import lombok.*;
import ro.ubb.boardgameapp.model.Gender;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString(callSuper = true)
public class UserDto extends BaseDto {
    private String firstName;
    private String lastName;
    private Date birthday;
    private Gender gender;
    private String email;
    private String username;

}
