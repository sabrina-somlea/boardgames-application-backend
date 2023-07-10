package ro.ubb.boardgameapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude="boardGames")
@Builder
public class User extends BaseEntity<UUID> implements UserDetails {

    private String firstName;
    private String lastName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private Gender gender;
    private String email;
    @Column(unique = true)
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "user_boardgames",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "boardgame_id")
    )
    private Set<BoardGame> boardGames = new HashSet<>();

    //for roles
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
