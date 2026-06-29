package com.nnk.springboot.api;

import com.nnk.springboot.controller.BidListController;
import com.nnk.springboot.model.BidListModel;
import com.nnk.springboot.service.BidListService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * API REST/JSON exposant le CRUD des offres (bids) ({@link BidListModel}).
 * <p>
 * Contrairement au {@link BidListController} (orienté vues Thymeleaf), ce
 * contrôleur renvoie des représentations JSON et utilise les verbes HTTP
 * standard. Toutes les routes sont préfixées par {@code /api/bidlist}.
 * La conversion entité/Model reste assurée par le {@link BidListService}.
 */
@RestController
@RequestMapping("/api/bidlist")
public class BidListRestController {

    private final BidListService bidListService;

    /**
     * Constructeur par défaut.
     *
     * @param bidListService service métier, injecté par Spring
     */
    public BidListRestController(BidListService bidListService) {
        this.bidListService = bidListService;
    }

    /**
     * Récupère toutes les offres.
     *
     * @return la liste des offres au format JSON (statut 200)
     */
    @GetMapping
    public List<BidListModel> getAll() {
        return bidListService.findAll();
    }

    /**
     * Récupère une offre par son identifiant.
     *
     * @param id identifiant de l'offre
     * @return l'offre (statut 200) ou un statut 404 si elle n'existe pas
     */
    @GetMapping("/{id}")
    public ResponseEntity<BidListModel> getById(@PathVariable("id") Integer id) {
        return bidListService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Crée une nouvelle offre.
     *
     * @param bidList l'offre à créer, désérialisée depuis le corps JSON et validée
     * @return l'offre créée (statut 201) avec l'en-tête {@code Location} vers la ressource
     */
    @PostMapping
    public ResponseEntity<BidListModel> create(@Valid @RequestBody BidListModel bidList) {
        BidListModel saved = bidListService.save(bidList);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getBidListId())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    /**
     * Met à jour une offre existante.
     *
     * @param id identifiant de l'offre à mettre à jour
     * @param bidList les nouvelles données, désérialisées depuis le corps JSON et validées
     * @return l'offre mise à jour (statut 200) ou un statut 404 si elle n'existe pas
     */
    @PutMapping("/{id}")
    public ResponseEntity<BidListModel> update(@PathVariable("id") Integer id,
                                               @Valid @RequestBody BidListModel bidList) {
        if (bidListService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        bidList.setBidListId(id);
        return ResponseEntity.ok(bidListService.save(bidList));
    }

    /**
     * Supprime une offre.
     *
     * @param id identifiant de l'offre à supprimer
     * @return un statut 204 (No Content) en cas de succès, ou 404 si elle n'existe pas
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        if (bidListService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        bidListService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
