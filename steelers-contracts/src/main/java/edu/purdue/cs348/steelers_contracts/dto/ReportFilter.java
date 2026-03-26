package edu.purdue.cs348.steelers_contracts.dto;

import org.springframework.format.annotation.NumberFormat;

public class ReportFilter {

    private Long teamId;
    private Long positionId;
    private Integer minAge;
    private Integer maxAge;

    @NumberFormat(pattern = "#.##")
    private Double minCapHit;

    @NumberFormat(pattern = "#.##")
    private Double maxCapHit;

    private String contractStatus;

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public Double getMinCapHit() {
        return minCapHit;
    }

    public void setMinCapHit(Double minCapHit) {
        this.minCapHit = minCapHit;
    }

    public Double getMaxCapHit() {
        return maxCapHit;
    }

    public void setMaxCapHit(Double maxCapHit) {
        this.maxCapHit = maxCapHit;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }
}
