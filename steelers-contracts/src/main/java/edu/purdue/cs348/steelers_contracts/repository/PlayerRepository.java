package edu.purdue.cs348.steelers_contracts.repository;

import edu.purdue.cs348.steelers_contracts.model.Player;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import jakarta.persistence.LockModeType;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    @EntityGraph(attributePaths = {"team", "position"})
    List<Player> findAllByOrderByLastNameAscFirstNameAsc();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Player> findByPlayerId(Long playerId);
}
