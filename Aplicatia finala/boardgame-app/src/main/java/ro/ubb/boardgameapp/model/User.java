package ro.ubb.boardgameapp.model;

import jakarta.persistence.Entity;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class User extends BaseEntity<UUID> {
    private String firstName;
    private String lastName;
    private Date birthday;
    private Gender gender;
    private String email;
    private String username;
    private String password;


}
