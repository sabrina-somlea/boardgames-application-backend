package ro.ubb.boardgameapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name="user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "boardGames")
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
    @Lob
    @Column(name = "profile_image", columnDefinition = "bytea")
    private byte[] profileImage;

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
    @JsonIgnore
    private Set<BoardGame> boardGames = new HashSet<>();

    @ManyToMany
            (fetch = FetchType.LAZY,
                    cascade = {
                            CascadeType.PERSIST,
                            CascadeType.MERGE
                    })
    @JoinTable(name="table_friends",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="friend_id")
    )

    @JsonIgnore
    private Set<User> friends;

//    @ManyToMany
//    @JoinTable(name="table_friends",
//            joinColumns=@JoinColumn(name="friend_id"),
//            inverseJoinColumns=@JoinColumn(name="user_id")
//    )
//
//    @JsonIgnore
//    private Set<User> friendOf;

    @ManyToMany (fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name="table_friends_requests",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="friend_id")
    )

    @JsonIgnore
    private Set<User> friendsRequests;

    @ManyToMany
    @JoinTable(name="table_friends_requests",
            joinColumns=@JoinColumn(name="friend_id"),
            inverseJoinColumns=@JoinColumn(name="user_id")
    )
    @JsonIgnore
    private Set<User> friendRequestOf;


    @ManyToMany(fetch = FetchType.LAZY
            , mappedBy = "players")
    @JsonIgnore
    private Set<BoardGameSession> usersSessions = new HashSet<>();

    @OneToMany(mappedBy = "winner")
    @JsonIgnore
    private Set<BoardGameSession> wonSessions;


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
    public void removeBoardGame(BoardGame boardGame){
        this.boardGames.remove(boardGame);
        boardGame.getUsers().remove(this);
    }
}
