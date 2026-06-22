package com.nnk.springboot.controller;

import com.nnk.springboot.model.TradeModel;
import com.nnk.springboot.service.TradeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur web gérant le CRUD des transactions (trades) ({@link TradeModel}).
 * <p>
 * S'appuie sur le {@link TradeService}, qui assure la conversion entre les Models
 * (utilisés par les vues Thymeleaf) et les entités persistées.
 * Les vues associées se trouvent dans {@code templates/trade/}.
 */
@Controller
public class TradeController {

    private final TradeService tradeService;

    /**
     * @param tradeService service métier, injecté par Spring
     */
    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    /**
     * Affiche la liste de tous les éléments.
     *
     * @param model modèle de la vue, alimenté avec l'attribut {@code trades}
     * @return la vue {@code trade/list}
     */
    @RequestMapping("/trade/list")
    public String home(Model model) {
        model.addAttribute("trades", tradeService.findAll());
        return "trade/list";
    }

    /**
     * Affiche le formulaire de création.
     *
     * @param trade objet vide lié au formulaire (attribut {@code trade})
     * @return la vue {@code trade/add}
     */
    @GetMapping("/trade/add")
    public String addForm(@ModelAttribute("trade") TradeModel trade) {
        return "trade/add";
    }

    /**
     * Valide puis enregistre un nouvel élément.
     *
     * @param trade données soumises par le formulaire, validées via {@code @Valid}
     * @param result résultat de la validation (erreurs de binding éventuelles)
     * @param model modèle de la vue
     * @return une redirection vers {@code /trade/list} en cas de succès,
     * sinon la vue {@code trade/add}
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid @ModelAttribute("trade") TradeModel trade,
                           BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "trade/add";
        }

        tradeService.save(trade);
        return "redirect:/trade/list";
    }

    /**
     * Affiche le formulaire de modification d'un élément existant.
     *
     * @param id identifiant de l'élément à modifier
     * @param model modèle de la vue, alimenté avec l'attribut {@code trade}
     * @return la vue {@code trade/update}
     * @throws IllegalArgumentException si aucun élément ne correspond à l'identifiant
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        TradeModel trade = tradeService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    /**
     * Valide puis met à jour un élément existant.
     *
     * @param id identifiant de l'élément à mettre à jour
     * @param trade données soumises par le formulaire, validées via {@code @Valid}
     * @param result résultat de la validation
     * @param model modèle de la vue
     * @return une redirection vers {@code /trade/list} en cas de succès,
     * sinon la vue {@code trade/update}
     */
    @PostMapping("/trade/update/{id}")
    public String update(@PathVariable("id") Integer id,
                         @Valid @ModelAttribute("trade") TradeModel trade,
                         BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "trade/update";
        }

        trade.setTradeId(id);
        tradeService.save(trade);
        return "redirect:/trade/list";
    }

    /**
     * Supprime un élément.
     *
     * @param id identifiant de l'élément à supprimer
     * @param model modèle de la vue
     * @return une redirection vers {@code /trade/list}
     * @throws IllegalArgumentException si aucun élément ne correspond à l'identifiant
     */
    @GetMapping("/trade/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        tradeService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
        tradeService.deleteById(id);
        return "redirect:/trade/list";
    }
}
