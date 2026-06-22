package com.nnk.springboot.controller;

import com.nnk.springboot.model.RatingModel;
import com.nnk.springboot.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur web gérant le CRUD des notations ({@link RatingModel}).
 * <p>
 * S'appuie sur le {@link RatingService}, qui assure la conversion entre les Models
 * (utilisés par les vues Thymeleaf) et les entités persistées.
 * Les vues associées se trouvent dans {@code templates/rating/}.
 */
@Controller
public class RatingController {

    private final RatingService ratingService;

    /**
     * @param ratingService service métier, injecté par Spring
     */
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    /**
     * Affiche la liste de tous les éléments.
     *
     * @param model modèle de la vue, alimenté avec l'attribut {@code ratings}
     * @return la vue {@code rating/list}
     */
    @RequestMapping("/rating/list")
    public String home(Model model) {
        model.addAttribute("ratings", ratingService.findAll());
        return "rating/list";
    }

    /**
     * Affiche le formulaire de création.
     *
     * @param rating objet vide lié au formulaire (attribut {@code rating})
     * @return la vue {@code rating/add}
     */
    @GetMapping("/rating/add")
    public String addForm(@ModelAttribute("rating") RatingModel rating) {
        return "rating/add";
    }

    /**
     * Valide puis enregistre un nouvel élément.
     *
     * @param rating données soumises par le formulaire, validées via {@code @Valid}
     * @param result résultat de la validation (erreurs de binding éventuelles)
     * @param model modèle de la vue
     * @return une redirection vers {@code /rating/list} en cas de succès,
     * sinon la vue {@code rating/add}
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid @ModelAttribute("rating") RatingModel rating,
                           BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "rating/add";
        }

        ratingService.save(rating);
        return "redirect:/rating/list";
    }

    /**
     * Affiche le formulaire de modification d'un élément existant.
     *
     * @param id identifiant de l'élément à modifier
     * @param model modèle de la vue, alimenté avec l'attribut {@code rating}
     * @return la vue {@code rating/update}
     * @throws IllegalArgumentException si aucun élément ne correspond à l'identifiant
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RatingModel rating = ratingService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    /**
     * Valide puis met à jour un élément existant.
     *
     * @param id identifiant de l'élément à mettre à jour
     * @param rating données soumises par le formulaire, validées via {@code @Valid}
     * @param result résultat de la validation
     * @param model modèle de la vue
     * @return une redirection vers {@code /rating/list} en cas de succès,
     * sinon la vue {@code rating/update}
     */
    @PostMapping("/rating/update/{id}")
    public String update(@PathVariable("id") Integer id,
                         @Valid @ModelAttribute("rating") RatingModel rating,
                         BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "rating/update";
        }

        rating.setId(id);
        ratingService.save(rating);
        return "redirect:/rating/list";
    }

    /**
     * Supprime un élément.
     *
     * @param id identifiant de l'élément à supprimer
     * @param model modèle de la vue
     * @return une redirection vers {@code /rating/list}
     * @throws IllegalArgumentException si aucun élément ne correspond à l'identifiant
     */
    @GetMapping("/rating/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        ratingService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
        ratingService.deleteById(id);
        return "redirect:/rating/list";
    }
}
