package edu.purdue.cs348.steelers_contracts.service;

import edu.purdue.cs348.steelers_contracts.model.Team;
import edu.purdue.cs348.steelers_contracts.repository.TeamRepository;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> getAllTeams() {
        List<Team> teams = teamRepository.findAll();
        teams.sort(Comparator.comparing(Team::getTeamName));
        return teams;
    }
}
