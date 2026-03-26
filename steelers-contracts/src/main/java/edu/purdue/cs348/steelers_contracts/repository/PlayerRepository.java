package edu.purdue.cs348.steelers_contracts.repository;

import edu.purdue.cs348.steelers_contracts.model.Player;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    @EntityGraph(attributePaths = {"team", "position"})
    List<Player> findAllByOrderByLastNameAscFirstNameAsc();
}
