package ro.ubb.boardgameapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
@Entity
@Table(name = "boardgames_sessions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BoardGameSession extends BaseEntity<UUID>{
    private Date sessionDate;

    @ManyToOne (cascade = {CascadeType.PERSIST,
            CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn( name="boardgame_id")
    private BoardGame boardGame;
    @ManyToOne (cascade = {CascadeType.PERSIST,
            CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn( name = "user_id")
    private User winner;
    @ManyToMany (fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "player_session",
            joinColumns = @JoinColumn(name = "session_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )

    @JsonIgnore
    private Set<User> players;

    @PreRemove
    private void removeUserAssociations() {
        for (User user : this.players) {
            user.getUsersSessions().remove(this);
        }


    }

    }







