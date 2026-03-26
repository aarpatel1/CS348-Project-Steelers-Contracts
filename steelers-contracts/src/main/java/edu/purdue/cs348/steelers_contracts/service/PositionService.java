package edu.purdue.cs348.steelers_contracts.service;

import edu.purdue.cs348.steelers_contracts.model.Position;
import edu.purdue.cs348.steelers_contracts.repository.PositionRepository;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PositionService {

    private final PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public List<Position> getAllPositions() {
        List<Position> positions = positionRepository.findAll();
        positions.sort(Comparator.comparing(Position::getPositionName));
        return positions;
    }
}
