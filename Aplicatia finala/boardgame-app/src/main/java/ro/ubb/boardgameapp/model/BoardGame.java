package ro.ubb.boardgameapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "boardgames")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BoardGame extends BaseEntity<UUID> {
    //    @JsonIgnore
//    private UUID id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    @Column(columnDefinition="text")
    private String description;
    @JsonProperty("year_published")
    private int yearPublished;
    @JsonProperty("min_players")
    private int minPlayers;
    @JsonProperty("max_players")
    private int maxPlayers;
    @JsonProperty("min_playtime")
    private int minPlayTime;
    @JsonProperty("max_playtime")
    private int maxPlayTime;
    @JsonProperty("image_url")
    private String imageUrl;

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof BoardGame other))
            return false;
        return this.name.equals(other.name);
    }

    @Override
    public final int hashCode() {
        int result = 17;
        if (name != null) {
            result = 31 * result + name.hashCode();
        }
        return result;
    }

    //categories
    @ManyToMany(fetch = FetchType.LAZY
            , mappedBy = "boardGames")

    @JsonIgnore
    private Set<User> users = new HashSet<>();


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy="boardGame")
    @JsonIgnore
    private Set<BoardGameSession> boardGamesSession;
    @PreRemove
    private void removeUserAssociations() {
        for (User user: this.users) {
            user.getBoardGames().remove(this);
        }
    }


}
