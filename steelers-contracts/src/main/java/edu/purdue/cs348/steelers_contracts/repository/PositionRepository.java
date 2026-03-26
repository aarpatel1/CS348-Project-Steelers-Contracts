package edu.purdue.cs348.steelers_contracts.repository;

import edu.purdue.cs348.steelers_contracts.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {
}
