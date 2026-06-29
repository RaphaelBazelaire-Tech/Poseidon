package com.nnk.springboot.api;

import com.nnk.springboot.controller.CurveController;
import com.nnk.springboot.model.CurvePointModel;
import com.nnk.springboot.service.CurvePointService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * API REST/JSON exposant le CRUD des points de courbe ({@link CurvePointModel}).
 * <p>
 * Contrairement au {@link CurveController} (orienté vues Thymeleaf), ce contrôleur
 * renvoie des représentations JSON et utilise les verbes HTTP standard. Toutes les
 * routes sont préfixées par {@code /api/curvepoint}. La conversion entité/Model
 * reste assurée par le {@link CurvePointService}.
 */
@RestController
@RequestMapping("/api/curvePoint")
public class CurvePointRestController {

    private final CurvePointService curvePointService;

    /**
     * Constructeur par défaut.
     *
     * @param curvePointService service métier, injecté par Spring
     */
    public CurvePointRestController(CurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }

    /**
     * Récupère tous les points de courbe.
     *
     * @return la liste des points de courbe au format JSON (statut 200)
     */
    @GetMapping
    public List<CurvePointModel> getAll() {
        return curvePointService.findAll();
    }

    /**
     * Récupère un point de courbe par son identifiant.
     *
     * @param id identifiant du point de courbe
     * @return le point de courbe (statut 200) ou un statut 404 s'il n'existe pas
     */
    @GetMapping("/{id}")
    public ResponseEntity<CurvePointModel> getById(@PathVariable Integer id) {
        return curvePointService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Crée un nouveau point de courbe.
     *
     * @param curvePoint le point de courbe à créer, désérialisé depuis le corps JSON et validé
     * @return le point de courbe créé (statut 201) avec l'en-tête {@code Location} vers la ressource
     */
    @PostMapping
    public ResponseEntity<CurvePointModel> create(@Valid @RequestBody CurvePointModel curvePoint) {
        CurvePointModel saved = curvePointService.save(curvePoint);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(saved);
    }

    /**
     * Met à jour un point de courbe existant.
     *
     * @param id identifiant du point de courbe à mettre à jour
     * @param curvePoint les nouvelles données, désérialisées depuis le corps JSON et validées
     * @return le point de courbe mis à jour (statut 200) ou un statut 404 s'il n'existe pas
     */
    @PutMapping("/{id}")
    public ResponseEntity<CurvePointModel> update(@PathVariable("id") Integer id,
                                                  @Valid @RequestBody CurvePointModel curvePoint) {
        if (curvePointService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        curvePoint.setId(id);
        return ResponseEntity.ok(curvePointService.save(curvePoint));
    }

    /**
     * Supprime un point de courbe.
     *
     * @param id identifiant du point de courbe à supprimer
     * @return un statut 204 (No Content) en cas de succès, ou 404 s'il n'existe pas
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        if (curvePointService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        curvePointService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
