package ro.ubb.boardgameapp.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import ro.ubb.boardgameapp.controller.BirthDate;
import ro.ubb.boardgameapp.controller.UniqueUsername;
import ro.ubb.boardgameapp.model.Gender;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString(callSuper = true)
public class UserDto extends BaseDto {
    @NotBlank(message = "Please enter a valid name")
    @Size(min = 2, message = "First Name must be at least 2 characters")
    @Size(max = 101, message = "Your First Name is too long")
    @Pattern(regexp = "[a-zA-Z- ]*")
    private String firstName;
    @NotBlank(message = "Please enter a valid name")
    @Size(min = 2, message = "Last Name must be at least 2 characters")
    @Size(max = 101, message = "Your Last Name is too long")
    @Pattern(regexp = "[a-zA-Z- ]*")
    private String lastName;
    @NotNull(message = "Please enter a valid date")
    @PastOrPresent(message = "date of birth must be less than today")
    @BirthDate(message="Wow! You lived more than 150 years? Please select a valid date")
    private Date birthday;
    @NotNull(message = "Please chose a gender")
    private Gender gender;
    @NotNull(message = "Please enter a valid email")
    @Email(message = "Please enter a valid email")
    private String email;
    @Pattern(regexp = "[a-zA-Z0-9_ -.]*")
    @NotBlank(message = "Please enter a valid username")
    @UniqueUsername(message ="This username already exists. Please choose another one")
    private String username;
    @NotBlank(message = "Please enter a password")
    private String password;

}
