package com.nnk.springboot.api;

import com.nnk.springboot.controller.TradeController;
import com.nnk.springboot.model.TradeModel;
import com.nnk.springboot.service.TradeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * API REST/JSON exposant le CRUD des transactions (trades) ({@link TradeModel}).
 * <p>
 * Contrairement au {@link TradeController} (orienté vues Thymeleaf), ce contrôleur
 * renvoie des représentations JSON et utilise les verbes HTTP standard. Toutes les
 * routes sont préfixées par {@code /api/trade}. La conversion entité/Model reste
 * assurée par le {@link TradeService}.
 */
@RestController
@RequestMapping("/api/trade")
public class TradeRestController {

    private final TradeService tradeService;

    /**
     * Constructeur par défaut.
     *
     * @param tradeService service métier, injecté par Spring
     */
    public TradeRestController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    /**
     * Récupère toutes les transactions.
     *
     * @return la liste des transactions au format JSON (statut 200)
     */
    @GetMapping
    public List<TradeModel> getAll() {
        return tradeService.findAll();
    }

    /**
     * Récupère une transaction par son identifiant.
     *
     * @param id identifiant de la transaction
     * @return la transaction (statut 200) ou un statut 404 si elle n'existe pas
     */
    @GetMapping("/{id}")
    public ResponseEntity<TradeModel> getById(@PathVariable("id") Integer id) {
        return tradeService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Crée une nouvelle transaction.
     *
     * @param trade la transaction à créer, désérialisée depuis le corps JSON et validée
     * @return la transaction créée (statut 201) avec l'en-tête {@code Location} vers la ressource
     */
    @PostMapping
    public ResponseEntity<TradeModel> create(@Valid @RequestBody TradeModel trade) {
        TradeModel saved = tradeService.save(trade);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getTradeId())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    /**
     * Met à jour une transaction existante.
     *
     * @param id identifiant de la transaction à mettre à jour
     * @param trade les nouvelles données, désérialisées depuis le corps JSON et validées
     * @return la transaction mise à jour (statut 200) ou un statut 404 si elle n'existe pas
     */
    @PutMapping("/{id}")
    public ResponseEntity<TradeModel> update(@PathVariable("id") Integer id,
                                             @Valid @RequestBody TradeModel trade) {
        if (tradeService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        trade.setTradeId(id);
        return ResponseEntity.ok(tradeService.save(trade));
    }

    /**
     * Supprime une transaction.
     *
     * @param id identifiant de la transaction à supprimer
     * @return un statut 204 (No Content) en cas de succès, ou 404 si elle n'existe pas
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        if (tradeService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        tradeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
