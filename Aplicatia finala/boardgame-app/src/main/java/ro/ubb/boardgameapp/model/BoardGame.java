package ro.ubb.boardgameapp.model;

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
public class BoardGame extends BaseEntity<UUID> {
private String name;
private String description;
private int yearPublished;
private int minPlayers;
private int maxPlayers;
private int minPlayTime;
private int maxPlayTime;
//categories

    @ManyToMany(mappedBy = "boardGames")
    private Set<User> users = new HashSet<>();
}
