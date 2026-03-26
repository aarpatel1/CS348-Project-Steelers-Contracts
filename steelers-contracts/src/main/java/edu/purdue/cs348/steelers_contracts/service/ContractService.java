package edu.purdue.cs348.steelers_contracts.service;

import edu.purdue.cs348.steelers_contracts.dto.ReportFilter;
import edu.purdue.cs348.steelers_contracts.model.Contract;
import edu.purdue.cs348.steelers_contracts.model.Player;
import edu.purdue.cs348.steelers_contracts.repository.ContractRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ContractService {

    private final ContractRepository contractRepository;
    private final PlayerService playerService;

    public ContractService(ContractRepository contractRepository, PlayerService playerService) {
        this.contractRepository = contractRepository;
        this.playerService = playerService;
    }

    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    public Contract getContractById(Long contractId) {
        return contractRepository.findById(contractId)
                .orElseThrow(() -> new IllegalArgumentException("Contract not found: " + contractId));
    }

    public Contract createContract(Contract contract, Long playerId) {
        Player player = playerService.getPlayerById(playerId);
        contract.setPlayer(player);
        return contractRepository.save(contract);
    }

    public Contract updateContract(Long contractId, Contract updatedContract, Long playerId) {
        Contract existing = getContractById(contractId);
        Player player = playerService.getPlayerById(playerId);

        existing.setPlayer(player);
        existing.setStartYear(updatedContract.getStartYear());
        existing.setEndYear(updatedContract.getEndYear());
        existing.setBaseSalary(updatedContract.getBaseSalary());
        existing.setSigningBonus(updatedContract.getSigningBonus());
        existing.setCapHit(updatedContract.getCapHit());
        existing.setGuaranteedMoney(updatedContract.getGuaranteedMoney());
        existing.setContractStatus(updatedContract.getContractStatus());

        return contractRepository.save(existing);
    }

    public void deleteContract(Long contractId) {
        contractRepository.deleteById(contractId);
    }

    // Stage 2 report: apply user-selected filters in one place for easy demo explanation.
    public List<Contract> getFilteredContracts(ReportFilter filter) {
        return getAllContracts().stream()
                .filter(c -> filter.getTeamId() == null || c.getPlayer().getTeam().getTeamId().equals(filter.getTeamId()))
                .filter(c -> filter.getPositionId() == null || c.getPlayer().getPosition().getPositionId().equals(filter.getPositionId()))
                .filter(c -> filter.getMinAge() == null || c.getPlayer().getAge() >= filter.getMinAge())
                .filter(c -> filter.getMaxAge() == null || c.getPlayer().getAge() <= filter.getMaxAge())
                .filter(c -> filter.getMinCapHit() == null || c.getCapHit().compareTo(BigDecimal.valueOf(filter.getMinCapHit())) >= 0)
                .filter(c -> filter.getMaxCapHit() == null || c.getCapHit().compareTo(BigDecimal.valueOf(filter.getMaxCapHit())) <= 0)
                .filter(c -> filter.getContractStatus() == null || filter.getContractStatus().isBlank()
                        || c.getContractStatus().equalsIgnoreCase(filter.getContractStatus()))
                .collect(Collectors.toList());
    }

    public List<String> getAllContractStatuses() {
        return getAllContracts().stream()
                .map(Contract::getContractStatus)
                .distinct()
                .sorted()
                .toList();
    }
}
