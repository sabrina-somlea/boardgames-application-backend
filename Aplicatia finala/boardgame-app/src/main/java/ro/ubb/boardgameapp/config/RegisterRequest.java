package ro.ubb.boardgameapp.config;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.ubb.boardgameapp.model.Gender;

import java.util.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private Gender gender;
    private Date birthday;
    private String email;
    private String username;
    private String password;

}
