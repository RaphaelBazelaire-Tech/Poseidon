package com.nnk.springboot.api;

import com.nnk.springboot.controller.RatingController;
import com.nnk.springboot.model.RatingModel;
import com.nnk.springboot.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * API REST/JSON exposant le CRUD des notations ({@link RatingModel}).
 * <p>
 * Contrairement au {@link RatingController} (orienté vues Thymeleaf), ce contrôleur
 * renvoie des représentations JSON et utilise les verbes HTTP standard. Toutes les
 * routes sont préfixées par {@code /api/rating}. La conversion entité/Model reste
 * assurée par le {@link RatingService}.
 */
@RestController
@RequestMapping("/api/rating")
public class RatingRestController {

    private final RatingService ratingService;

    /**
     * Constructeur par défaut.
     *
     * @param ratingService service métier, injecté par Spring
     */
    public RatingRestController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    /**
     * Récupère toutes les notations.
     *
     * @return la liste des notations au format JSON (statut 200)
     */
    @GetMapping
    public List<RatingModel> getAll() {
        return ratingService.findAll();
    }

    /**
     * Récupère une notation par son identifiant.
     *
     * @param id identifiant de la notation
     * @return la notation (statut 200) ou un statut 404 si elle n'existe pas
     */
    @GetMapping("/{id}")
    public ResponseEntity<RatingModel> getById(@PathVariable("id") Integer id) {
        return ratingService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Crée une nouvelle notation.
     *
     * @param rating la notation à créer, désérialisée depuis le corps JSON et validée
     * @return la notation créée (statut 201) avec l'en-tête {@code Location} vers la ressource
     */
    @PostMapping
    public ResponseEntity<RatingModel> create(@Valid @RequestBody RatingModel rating) {
        RatingModel saved = ratingService.save(rating);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    /**
     * Met à jour une notation existante.
     *
     * @param id identifiant de la notation à mettre à jour
     * @param rating les nouvelles données, désérialisées depuis le corps JSON et validées
     * @return la notation mise à jour (statut 200) ou un statut 404 si elle n'existe pas
     */
    @PutMapping("/{id}")
    public ResponseEntity<RatingModel> update(@PathVariable("id") Integer id,
                                              @Valid @RequestBody RatingModel rating) {
        if (ratingService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        rating.setId(id);
        return ResponseEntity.ok(ratingService.save(rating));
    }

    /**
     * Supprime une notation.
     *
     * @param id identifiant de la notation à supprimer
     * @return un statut 204 (No Content) en cas de succès, ou 404 si elle n'existe pas
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        if (ratingService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ratingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
