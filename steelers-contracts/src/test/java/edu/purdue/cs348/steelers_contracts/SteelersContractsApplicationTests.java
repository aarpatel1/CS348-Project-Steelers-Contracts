package edu.purdue.cs348.steelers_contracts;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.purdue.cs348.steelers_contracts.model.Contract;
import edu.purdue.cs348.steelers_contracts.service.ContractService;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class SteelersContractsApplicationTests {

	@Autowired
	private ContractService contractService;

	@Test
	void contextLoads() {
	}

	@Test
	@Transactional
	void adjacentContractsAreAllowed() {
		Contract backToBack = buildContract(2026, 2027);
		assertDoesNotThrow(() -> contractService.createContract(backToBack, 1L));
	}

	@Test
	@Transactional
	void trueOverlapIsRejected() {
		// Build the exact scenario requested:
		// first contract [2025, 2027), then attempted overlapping [2026, 2028).
		contractService.deleteContract(1L);
		contractService.createContract(buildContract(2025, 2027), 1L);
		assertThrows(IllegalArgumentException.class, () -> contractService.createContract(buildContract(2026, 2028), 1L));
	}

	@Test
	@Transactional
	void updateKeepsSameYearsWithoutSelfConflict() {
		Contract existing = contractService.getContractById(1L);
		Contract updated = buildContract(existing.getStartYear(), existing.getEndYear());
		updated.setContractStatus("ACTIVE");
		updated.setBaseSalary(existing.getBaseSalary().add(new BigDecimal("1.00")));
		assertDoesNotThrow(() -> contractService.updateContract(existing.getContractId(), updated, 1L));
	}

	private Contract buildContract(int startYear, int endYear) {
		Contract contract = new Contract();
		contract.setStartYear(startYear);
		contract.setEndYear(endYear);
		contract.setBaseSalary(new BigDecimal("1000000.00"));
		contract.setSigningBonus(new BigDecimal("100000.00"));
		contract.setCapHit(new BigDecimal("1100000.00"));
		contract.setGuaranteedMoney(new BigDecimal("500000.00"));
		contract.setContractStatus("ACTIVE");
		return contract;
	}
}
