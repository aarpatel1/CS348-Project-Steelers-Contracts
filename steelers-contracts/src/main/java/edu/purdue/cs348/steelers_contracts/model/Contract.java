package edu.purdue.cs348.steelers_contracts.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Year;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "contracts")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    private Long contractId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @NotNull
    @Min(1950)
    @Column(name = "start_year", nullable = false)
    private Integer startYear;

    @NotNull
    @Min(1950)
    @Column(name = "end_year", nullable = false)
    private Integer endYear;

    @NotNull
    @DecimalMin(value = "0.00", inclusive = true)
    // Matches schema DECIMAL(20,2): up to 18 digits left of the decimal.
    @Digits(integer = 18, fraction = 2, message = "Value too large. Max is 18 digits before the decimal.")
    @Column(name = "base_salary", nullable = false, precision = 20, scale = 2)
    private BigDecimal baseSalary;

    @NotNull
    @DecimalMin(value = "0.00", inclusive = true)
    @Digits(integer = 18, fraction = 2, message = "Value too large. Max is 18 digits before the decimal.")
    @Column(name = "signing_bonus", nullable = false, precision = 20, scale = 2)
    private BigDecimal signingBonus;

    @NotNull
    @DecimalMin(value = "0.00", inclusive = true)
    @Digits(integer = 18, fraction = 2, message = "Value too large. Max is 18 digits before the decimal.")
    @Column(name = "cap_hit", nullable = false, precision = 20, scale = 2)
    private BigDecimal capHit;

    @NotNull
    @DecimalMin(value = "0.00", inclusive = true)
    @Digits(integer = 18, fraction = 2, message = "Value too large. Max is 18 digits before the decimal.")
    @Column(name = "guaranteed_money", nullable = false, precision = 20, scale = 2)
    private BigDecimal guaranteedMoney;

    @NotBlank
    @Column(name = "contract_status", nullable = false, length = 25)
    private String contractStatus;

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    public BigDecimal getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(BigDecimal baseSalary) {
        this.baseSalary = baseSalary;
    }

    public BigDecimal getSigningBonus() {
        return signingBonus;
    }

    public void setSigningBonus(BigDecimal signingBonus) {
        this.signingBonus = signingBonus;
    }

    public BigDecimal getCapHit() {
        return capHit;
    }

    public void setCapHit(BigDecimal capHit) {
        this.capHit = capHit;
    }

    public BigDecimal getGuaranteedMoney() {
        return guaranteedMoney;
    }

    public void setGuaranteedMoney(BigDecimal guaranteedMoney) {
        this.guaranteedMoney = guaranteedMoney;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public int getYearsRemaining() {
        int currentYear = Year.now().getValue();
        return Math.max(0, endYear - currentYear + 1);
    }
}
