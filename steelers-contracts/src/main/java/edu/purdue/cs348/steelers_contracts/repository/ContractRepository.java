package edu.purdue.cs348.steelers_contracts.repository;

import edu.purdue.cs348.steelers_contracts.model.Contract;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    @Override
    @EntityGraph(attributePaths = {"player", "player.team", "player.position"})
    List<Contract> findAll();

    @EntityGraph(attributePaths = {"player", "player.team", "player.position"})
    @Query("""
        SELECT c FROM Contract c
        JOIN c.player p
        WHERE (:teamId IS NULL OR p.team.teamId = :teamId)
          AND (:positionId IS NULL OR p.position.positionId = :positionId)
          AND (:minAge IS NULL OR p.age >= :minAge)
          AND (:maxAge IS NULL OR p.age <= :maxAge)
          AND (:minCapHit IS NULL OR c.capHit >= :minCapHit)
          AND (:maxCapHit IS NULL OR c.capHit <= :maxCapHit)
          AND (:contractStatus IS NULL OR LOWER(c.contractStatus) = LOWER(:contractStatus))
        ORDER BY p.lastName ASC, p.firstName ASC
        """)
    List<Contract> findFilteredContracts(
            @Param("teamId") Long teamId,
            @Param("positionId") Long positionId,
            @Param("minAge") Integer minAge,
            @Param("maxAge") Integer maxAge,
            @Param("minCapHit") BigDecimal minCapHit,
            @Param("maxCapHit") BigDecimal maxCapHit,
            @Param("contractStatus") String contractStatus
    );

    @Query("""
        SELECT COUNT(c) > 0 FROM Contract c
        WHERE c.player.playerId = :playerId
          AND (:excludeContractId IS NULL OR c.contractId <> :excludeContractId)
          AND c.startYear < :endYear
          AND c.endYear > :startYear
        """)
    boolean existsOverlappingContractYears(
            @Param("playerId") Long playerId,
            @Param("startYear") Integer startYear,
            @Param("endYear") Integer endYear,
            @Param("excludeContractId") Long excludeContractId
    );
}
