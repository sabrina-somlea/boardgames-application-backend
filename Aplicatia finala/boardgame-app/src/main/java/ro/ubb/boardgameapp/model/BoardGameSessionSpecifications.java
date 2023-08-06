package ro.ubb.boardgameapp.model;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class BoardGameSessionSpecifications {
    public static Specification<BoardGameSession> filterByCriteria(String boardGameName, Date sessionDate, String winnerName, String playerName, String username) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (sessionDate != null) {
                predicates.add(criteriaBuilder.equal(root.get("sessionDate"), sessionDate));
            }

            if (boardGameName != null && !boardGameName.isEmpty()) {
                Join<BoardGameSession, BoardGame> boardGameJoin = root.join("boardGame");
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(boardGameJoin.get("name")), "%" + boardGameName.toLowerCase() + "%"));
            }

            if (winnerName != null && !winnerName.isEmpty()) {
                Join<BoardGameSession, User> winnerJoin = root.join("winner");
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(winnerJoin.get("firstName")), "%" + winnerName.toLowerCase() + "%"));
            }

            if (playerName != null && !playerName.isEmpty()) {
                Join<BoardGameSession, User> playersJoin = root.join("players");
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(playersJoin.get("firstName")), "%" + playerName.toLowerCase() + "%"));
            }

            if (username != null && !username.isEmpty()) {
                Join<BoardGameSession, User> userJoin = root.join("players");
                predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(userJoin.get("username")), username.toLowerCase()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
