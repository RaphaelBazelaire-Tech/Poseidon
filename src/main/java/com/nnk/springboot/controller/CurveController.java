package com.nnk.springboot.controller;

import com.nnk.springboot.model.CurvePointModel;
import com.nnk.springboot.service.CurvePointService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur web gérant le CRUD des points de courbe ({@link CurvePointModel}).
 * <p>
 * S'appuie sur le {@link CurvePointService}, qui assure la conversion entre les Models
 * (utilisés par les vues Thymeleaf) et les entités persistées.
 * Les vues associées se trouvent dans {@code templates/curvePoint/}.
 */
@Controller
public class CurveController {

    private final CurvePointService curvePointService;

    /**
     * @param curvePointService service métier, injecté par Spring
     */
    public CurveController(CurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }

    /**
     * Affiche la liste de tous les éléments.
     *
     * @param model modèle de la vue, alimenté avec l'attribut {@code curvePoints}
     * @return la vue {@code curvePoint/list}
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        model.addAttribute("curvePoints", curvePointService.findAll());
        return "curvePoint/list";
    }

    /**
     * Affiche le formulaire de création.
     *
     * @param curvePoint objet vide lié au formulaire (attribut {@code curvePoint})
     * @return la vue {@code curvePoint/add}
     */
    @GetMapping("/curvePoint/add")
    public String addForm(@ModelAttribute("curvePoint")CurvePointModel curvePoint) {
        return "curvePoint/add";
    }

    /**
     * Valide puis enregistre un nouvel élément.
     *
     * @param curvePoint données soumises par le formulaire, validées via {@code @Valid}
     * @param result résultat de la validation (erreurs de binding éventuelles)
     * @param model modèle de la vue
     * @return une redirection vers {@code /curvePoint/list} en cas de succès,
     * sinon la vue {@code curvePoint/add}
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid @ModelAttribute("curvePoint") CurvePointModel curvePoint,
                           BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "curvePoint/add";
        }

        curvePointService.save(curvePoint);
        return "redirect:/curvePoint/list";
    }

    /**
     * Affiche le formulaire de modification d'un élément existant.
     *
     * @param id identifiant de l'élément à modifier
     * @param model modèle de la vue, alimenté avec l'attribut {@code curvePoint}
     * @return la vue {@code curvePoint/update}
     * @throws IllegalArgumentException si aucun élément ne correspond à l'identifiant
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        CurvePointModel curvePoint = curvePointService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid curve point Id:" + id));
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    /**
     * Valide puis met à jour un élément existant.
     *
     * @param id identifiant de l'élément à mettre à jour
     * @param curvePoint données soumises par le formulaire, validées via {@code @Valid}
     * @param result résultat de la validation
     * @param model modèle de la vue
     * @return une redirection vers {@code /curvePoint/list} en cas de succès,
     * sinon la vue {@code curvePoint/update}
     */
    @PostMapping("/curvePoint/update/{id}")
    public String update(@PathVariable("id") Integer id,
                         @Valid @ModelAttribute("curvePoint") CurvePointModel curvePoint,
                         BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "curvePoint/update";
        }

        curvePoint.setId(id);
        curvePointService.save(curvePoint);
        return "redirect:/curvePoint/list";
    }

    /**
     * Supprime un élément.
     *
     * @param id identifiant de l'élément à supprimer
     * @param model modèle de la vue
     * @return une redirection vers {@code /curvePoint/list}
     * @throws IllegalArgumentException si aucun élément ne correspond à l'identifiant
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        curvePointService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid curve point Id:" + id));
        curvePointService.deleteById(id);
        return "redirect:/curvePoint/list";
    }
}
