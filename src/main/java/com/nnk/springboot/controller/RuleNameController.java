package com.nnk.springboot.controller;

import com.nnk.springboot.model.RuleNameModel;
import com.nnk.springboot.service.RuleNameService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur web gérant le CRUD des règles métier ({@link RuleNameModel}).
 * <p>
 * S'appuie sur le {@link RuleNameService}, qui assure la conversion entre les Models
 * (utilisés par les vues Thymeleaf) et les entités persistées.
 * Les vues associées se trouvent dans {@code templates/ruleName/}.
 */
@Controller
public class RuleNameController {

    private final RuleNameService ruleNameService;

    /**
     * @param ruleNameService service métier, injecté par Spring
     */
    public RuleNameController(RuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }

    /**
     * Affiche la liste de tous les éléments.
     *
     * @param model modèle de la vue, alimenté avec l'attribut {@code ruleNames}
     * @return la vue {@code ruleName/list}
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        model.addAttribute("ruleNames", ruleNameService.findAll());
        return "ruleName/list";
    }

    /**
     * Affiche le formulaire de création.
     *
     * @param ruleName objet vide lié au formulaire (attribut {@code ruleName})
     * @return la vue {@code ruleName/add}
     */
    @GetMapping("/ruleName/add")
    public String addForm(@ModelAttribute("ruleName") RuleNameModel ruleName) {
        return "ruleName/add";
    }

    /**
     * Valide puis enregistre un nouvel élément.
     *
     * @param ruleName données soumises par le formulaire, validées via {@code @Valid}
     * @param result résultat de la validation (erreurs de binding éventuelles)
     * @param model modèle de la vue
     * @return une redirection vers {@code /ruleName/list} en cas de succès,
     * sinon la vue {@code ruleName/add}
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid @ModelAttribute("ruleName") RuleNameModel ruleName,
                           BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ruleName/add";
        }
        ruleNameService.save(ruleName);
        return "redirect:/ruleName/list";
    }

    /**
     * Affiche le formulaire de modification d'un élément existant.
     *
     * @param id identifiant de l'élément à modifier
     * @param model modèle de la vue, alimenté avec l'attribut {@code ruleName}
     * @return la vue {@code ruleName/update}
     * @throws IllegalArgumentException si aucun élément ne correspond à l'identifiant
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RuleNameModel ruleName = ruleNameService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid rule name Id:" + id));
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    /**
     * Valide puis met à jour un élément existant.
     *
     * @param id identifiant de l'élément à mettre à jour
     * @param ruleName données soumises par le formulaire, validées via {@code @Valid}
     * @param result résultat de la validation
     * @param model modèle de la vue
     * @return une redirection vers {@code /ruleName/list} en cas de succès,
     * sinon la vue {@code ruleName/update}
     */
    @PostMapping("/ruleName/update/{id}")
    public String update(@PathVariable("id") Integer id,
                         @Valid @ModelAttribute("ruleName") RuleNameModel ruleName,
                         BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ruleName/update";
        }
        ruleName.setId(id);
        ruleNameService.save(ruleName);
        return "redirect:/ruleName/list";
    }

    /**
     * Supprime un élément.
     *
     * @param id identifiant de l'élément à supprimer
     * @param model modèle de la vue
     * @return une redirection vers {@code /ruleName/list}
     * @throws IllegalArgumentException si aucun élément ne correspond à l'identifiant
     */
    @GetMapping("/ruleName/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        ruleNameService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid rule name Id:" + id));
        ruleNameService.deleteById(id);
        return "redirect:/ruleName/list";
    }
}
