package com.nnk.springboot.api;

import com.nnk.springboot.controller.RuleNameController;
import com.nnk.springboot.model.RuleNameModel;
import com.nnk.springboot.service.RuleNameService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * API REST/JSON exposant le CRUD des règles métier ({@link RuleNameModel}).
 * <p>
 * Contrairement au {@link RuleNameController} (orienté vues Thymeleaf), ce contrôleur
 * renvoie des représentations JSON et utilise les verbes HTTP standard. Toutes les
 * routes sont préfixées par {@code /api/rulename}. La conversion entité/Model reste
 * assurée par le {@link RuleNameService}.
 */
@RestController
@RequestMapping("/api/rulename")
public class RuleNameRestController {

    private final RuleNameService ruleNameService;

    /**
     * Constructeur par défaut.
     *
     * @param ruleNameService service métier, injecté par Spring
     */
    public RuleNameRestController(RuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }

    /**
     * Récupère toutes les règles.
     *
     * @return la liste des règles au format JSON (statut 200)
     */
    @GetMapping
    public List<RuleNameModel> getAll() {
        return ruleNameService.findAll();
    }

    /**
     * Récupère une règle par son identifiant.
     *
     * @param id identifiant de la règle
     * @return la règle (statut 200) ou un statut 404 si elle n'existe pas
     */
    @GetMapping("/{id}")
    public ResponseEntity<RuleNameModel> getById(@PathVariable("id") Integer id) {
        return ruleNameService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Crée une nouvelle règle.
     *
     * @param ruleName la règle à créer, désérialisée depuis le corps JSON et validée
     * @return la règle créée (statut 201) avec l'en-tête {@code Location} vers la ressource
     */
    @PostMapping
    public ResponseEntity<RuleNameModel> create(@Valid @RequestBody RuleNameModel ruleName) {
        RuleNameModel saved = ruleNameService.save(ruleName);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    /**
     * Met à jour une règle existante.
     *
     * @param id identifiant de la règle à mettre à jour
     * @param ruleName les nouvelles données, désérialisées depuis le corps JSON et validées
     * @return la règle mise à jour (statut 200) ou un statut 404 si elle n'existe pas
     */
    @PutMapping("/{id}")
    public ResponseEntity<RuleNameModel> update(@PathVariable("id") Integer id,
                                                @Valid @RequestBody RuleNameModel ruleName) {
        if (ruleNameService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ruleName.setId(id);
        return ResponseEntity.ok(ruleNameService.save(ruleName));
    }

    /**
     * Supprime une règle.
     *
     * @param id identifiant de la règle à supprimer
     * @return un statut 204 (No Content) en cas de succès, ou 404 si elle n'existe pas
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        if (ruleNameService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ruleNameService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
