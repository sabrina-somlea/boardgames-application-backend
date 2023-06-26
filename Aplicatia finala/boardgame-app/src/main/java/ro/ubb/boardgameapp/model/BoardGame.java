package ro.ubb.boardgameapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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
    @Override
    public boolean equals(Object o){
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

    @ManyToMany(mappedBy = "boardGames")
    private Set<User> users = new HashSet<>();

}
