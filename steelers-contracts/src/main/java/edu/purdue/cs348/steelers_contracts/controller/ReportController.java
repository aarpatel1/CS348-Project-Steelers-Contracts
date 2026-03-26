package edu.purdue.cs348.steelers_contracts.controller;

import edu.purdue.cs348.steelers_contracts.dto.ReportFilter;
import edu.purdue.cs348.steelers_contracts.service.ContractService;
import edu.purdue.cs348.steelers_contracts.service.PositionService;
import edu.purdue.cs348.steelers_contracts.service.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reports")
public class ReportController {

    private final ContractService contractService;
    private final TeamService teamService;
    private final PositionService positionService;

    public ReportController(ContractService contractService, TeamService teamService, PositionService positionService) {
        this.contractService = contractService;
        this.teamService = teamService;
        this.positionService = positionService;
    }

    @GetMapping
    public String report(@ModelAttribute("filter") ReportFilter filter, Model model) {
        model.addAttribute("rows", contractService.getFilteredContracts(filter));
        model.addAttribute("teams", teamService.getAllTeams());
        model.addAttribute("positions", positionService.getAllPositions());
        model.addAttribute("contractStatuses", contractService.getAllContractStatuses());
        return "reports/contract-report";
    }
}
