package edu.purdue.cs348.steelers_contracts.service;

import edu.purdue.cs348.steelers_contracts.model.Player;
import edu.purdue.cs348.steelers_contracts.repository.PlayerRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getAllPlayersForDropdown() {
        return playerRepository.findAllByOrderByLastNameAscFirstNameAsc();
    }

    public Player getPlayerById(Long playerId) {
        return playerRepository.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("Player not found: " + playerId));
    }

    public Player getPlayerByIdForUpdate(Long playerId) {
        return playerRepository.findByPlayerId(playerId)
                .orElseThrow(() -> new IllegalArgumentException("Player not found: " + playerId));
    }
}
