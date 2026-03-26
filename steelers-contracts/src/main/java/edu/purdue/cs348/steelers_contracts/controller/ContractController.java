package edu.purdue.cs348.steelers_contracts.controller;

import edu.purdue.cs348.steelers_contracts.model.Contract;
import edu.purdue.cs348.steelers_contracts.service.ContractService;
import edu.purdue.cs348.steelers_contracts.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/contracts")
public class ContractController {

    private final ContractService contractService;
    private final PlayerService playerService;

    public ContractController(ContractService contractService, PlayerService playerService) {
        this.contractService = contractService;
        this.playerService = playerService;
    }

    @GetMapping
    public String listContracts(Model model) {
        model.addAttribute("contracts", contractService.getAllContracts());
        return "contracts/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("contract", new Contract());
        model.addAttribute("players", playerService.getAllPlayersForDropdown());
        model.addAttribute("formAction", "/contracts");
        model.addAttribute("pageTitle", "Create Contract");
        return "contracts/form";
    }

    @PostMapping
    public String createContract(@Valid @ModelAttribute Contract contract,
                                  BindingResult bindingResult,
                                  @RequestParam Long playerId,
                                  Model model) {
        if (bindingResult.hasErrors()) {
            // Repopulate dropdown values and ensure the selected player shows up.
            contract.setPlayer(playerService.getPlayerById(playerId));
            model.addAttribute("players", playerService.getAllPlayersForDropdown());
            model.addAttribute("formAction", "/contracts");
            model.addAttribute("pageTitle", "Create Contract");
            return "contracts/form";
        }

        contractService.createContract(contract, playerId);
        return "redirect:/contracts";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("contract", contractService.getContractById(id));
        model.addAttribute("players", playerService.getAllPlayersForDropdown());
        model.addAttribute("formAction", "/contracts/" + id + "/edit");
        model.addAttribute("pageTitle", "Edit Contract");
        return "contracts/form";
    }

    @PostMapping("/{id}/edit")
    public String updateContract(@PathVariable Long id,
                                   @Valid @ModelAttribute Contract contract,
                                   BindingResult bindingResult,
                                   @RequestParam Long playerId,
                                   Model model) {
        if (bindingResult.hasErrors()) {
            contract.setPlayer(playerService.getPlayerById(playerId));
            model.addAttribute("players", playerService.getAllPlayersForDropdown());
            model.addAttribute("formAction", "/contracts/" + id + "/edit");
            model.addAttribute("pageTitle", "Edit Contract");
            return "contracts/form";
        }

        contractService.updateContract(id, contract, playerId);
        return "redirect:/contracts";
    }

    @PostMapping("/{id}/delete")
    public String deleteContract(@PathVariable Long id) {
        contractService.deleteContract(id);
        return "redirect:/contracts";
    }
}
