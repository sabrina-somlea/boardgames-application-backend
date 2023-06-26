package ro.ubb.boardgameapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
@ToString
public abstract class BaseEntity<ID extends Serializable> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false)
    @JsonIgnore
    private ID id;
}

