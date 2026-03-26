package edu.purdue.cs348.steelers_contracts.repository;

import edu.purdue.cs348.steelers_contracts.model.Contract;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    @Override
    @EntityGraph(attributePaths = {"player", "player.team", "player.position"})
    List<Contract> findAll();
}
