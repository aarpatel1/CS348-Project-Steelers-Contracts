package edu.purdue.cs348.steelers_contracts.service;

import edu.purdue.cs348.steelers_contracts.dto.ReportFilter;
import edu.purdue.cs348.steelers_contracts.model.Contract;
import edu.purdue.cs348.steelers_contracts.model.Player;
import edu.purdue.cs348.steelers_contracts.repository.ContractRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Contract createContract(Contract contract, Long playerId) {
        // Locking the player row in this transaction prevents concurrent users from
        // passing overlap checks at the same time and inserting conflicting contracts.
        Player player = playerService.getPlayerByIdForUpdate(playerId);
        validateNoYearOverlap(playerId, contract.getStartYear(), contract.getEndYear(), null);
        contract.setPlayer(player);
        return contractRepository.save(contract);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Contract updateContract(Long contractId, Contract updatedContract, Long playerId) {
        Contract existing = getContractById(contractId);
        Player player = playerService.getPlayerByIdForUpdate(playerId);
        validateNoYearOverlap(playerId, updatedContract.getStartYear(), updatedContract.getEndYear(), contractId);

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

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteContract(Long contractId) {
        contractRepository.deleteById(contractId);
    }

    // JPA binds each filter as a typed parameter instead of string-concatenating SQL,
    // which blocks SQL injection attempts in report/search inputs.
    public List<Contract> getFilteredContracts(ReportFilter filter) {
        String status = normalizeStatus(filter.getContractStatus());
        return contractRepository.findFilteredContracts(
                filter.getTeamId(),
                filter.getPositionId(),
                filter.getMinAge(),
                filter.getMaxAge(),
                filter.getMinCapHit() == null ? null : BigDecimal.valueOf(filter.getMinCapHit()),
                filter.getMaxCapHit() == null ? null : BigDecimal.valueOf(filter.getMaxCapHit()),
                status
        );
    }

    public List<String> getAllContractStatuses() {
        return getAllContracts().stream()
                .map(Contract::getContractStatus)
                .distinct()
                .sorted()
                .toList();
    }

    private String normalizeStatus(String status) {
        if (status == null || status.isBlank()) {
            return null;
        }
        return status.trim();
    }

    private void validateNoYearOverlap(Long playerId, Integer startYear, Integer endYear, Long excludeContractId) {
        if (startYear != null && endYear != null && startYear >= endYear) {
            throw new IllegalArgumentException("Start year must be less than end year.");
        }
        // Overlap uses interval logic [startYear, endYear):
        // existing.start < new.end AND existing.end > new.start
        // This allows adjacent contracts where one ends exactly when the next starts.
        boolean overlap = contractRepository.existsOverlappingContractYears(playerId, startYear, endYear, excludeContractId);
        if (overlap) {
            throw new IllegalArgumentException("This player already has a contract that overlaps those years.");
        }
    }
}
