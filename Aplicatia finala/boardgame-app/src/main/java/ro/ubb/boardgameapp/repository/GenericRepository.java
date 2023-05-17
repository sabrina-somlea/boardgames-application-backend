package ro.ubb.boardgameapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubb.boardgameapp.model.BaseEntity;

import java.io.Serializable;

public interface GenericRepository<T extends BaseEntity<ID>, ID extends Serializable> extends JpaRepository<T, ID> {
}
