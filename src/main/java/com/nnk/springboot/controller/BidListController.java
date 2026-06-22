package com.nnk.springboot.controller;

import com.nnk.springboot.model.BidListModel;
import com.nnk.springboot.service.BidListService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur web gérant le CRUD des offres (bids) ({@link BidListModel}).
 * <p>
 * S'appuie sur le {@link BidListService}, qui assure la conversion entre les Models
 * (utilisés par les vues Thymeleaf) et les entités persistées.
 * Les vues associées se trouvent dans {@code templates/bidList/}.
 */
@Controller
public class BidListController {

    private final BidListService bidListService;

    /**
     * @param bidListService service métier, injecté par Spring.
     */
    public BidListController(BidListService bidListService) {
        this.bidListService = bidListService;
    }

    /**
     * Affiche la liste de tous les éléments.
     *
     * @param model modèle de la vue, alimenté avec l'attribut {@code bidLists}
     * @return la vue {@code bidList/list}
     */
    @RequestMapping("/bidList/list")
    public String home(Model model) {
        model.addAttribute("bidLists", bidListService.findAll());
        return "bidList/list";
    }

    /**
     * Affiche le formulaire de création.
     *
     * @param bidList objet vide lié au formulaire (attribut {@code bidList})
     * @return la vue {@code bidList/add}
     */
    @GetMapping("/bidList/add")
    public String addForm(@ModelAttribute("bidList") BidListModel bidList) {
        return "bidList/add";
    }

    /**
     * Valide puis enregistre un nouvel élément.
     *
     * @param bidList données soumises par le formulaire, validées via {@code @Valid}
     * @param result résultat de la validation (erreurs de binding éventuelles)
     * @param model modèle de la vue
     * @return une redirection vers {@code /bidList/list} en cas de succès,
     * sinon la vue {@code bidList/add}
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid @ModelAttribute("bidList") BidListModel bidList,
                           BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "bidList/add";
        }
        bidListService.save(bidList);
        return "redirect:/bidList/list";
    }

    /**
     * Affiche le formulaire de modification d'un élément existant.
     *
     * @param id identifiant de l'élément à modifier
     * @param model modèle de la vue, alimenté avec l'attribut {@code bidList}
     * @return la vue {@code bidList/update}
     * @throws IllegalArgumentException si aucun élément ne correspond à l'identifiant
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        BidListModel bidList = bidListService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid bid list Id: " + id));
        model.addAttribute("bidList", bidList);
        return "bidList/update";
    }

    /**
     * Valide puis met à jour un élément existant.
     *
     * @param id identifiant de l'élément à mettre à jour
     * @param bidList données soumises par le formulaire, validées via {@code @Valid}
     * @param result résultat de la validation
     * @param model modèle de la vue
     * @return une redirection vers {@code /bidList/list} en cas de succès,
     * sinon la vue {@code bidList/update}
     */
    @PostMapping("/bidList/update/{id}")
    public String update(@PathVariable("id") Integer id,
                         @Valid @ModelAttribute("bidList") BidListModel bidList,
                         BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "bidList/update";
        }

        bidList.setBidListId(id);
        bidListService.save(bidList);
        return "redirect:/bidList/list";
    }

    /**
     * Supprime un élément.
     *
     * @param id identifiant de l'élément à supprimer
     * @param model modèle de la vue
     * @return une redirection vers {@code /bidList/list}
     * @throws IllegalArgumentException si aucun élément ne correspond à l'identifiant
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        BidListModel bidList = bidListService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid bid list Id: " + id));
        bidListService.deleteById(bidList.getBidListId());
        return "redirect:/bidList/list";
    }
}
