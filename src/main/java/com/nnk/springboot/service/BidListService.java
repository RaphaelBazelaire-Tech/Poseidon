package com.nnk.springboot.service;

import com.nnk.springboot.mapper.BidListMapper;
import com.nnk.springboot.model.BidListModel;
import com.nnk.springboot.repository.BidListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service métier pour les offres (bids).
 * <p>
 * Orchestre le {@link BidListRepository} et le {@link BidListMapper} : les méthodes
 * exposées travaillent avec des {@link BidListModel}, la conversion vers/depuis l'entité
 * {@link com.nnk.springboot.entity.BidListEntity} étant gérée en interne.
 */
@Service
public class BidListService {

    private final BidListRepository bidListRepository;
    private final BidListMapper bidListMapper;

    /**
     * Constructeur par défaut.
     *
     * @param bidListRepository repository d'accès aux données, injecté par Spring
     * @param bidListMapper convertisseur entité/Model, injecté par Spring
     */
    public BidListService(BidListRepository bidListRepository, BidListMapper bidListMapper) {
        this.bidListRepository = bidListRepository;
        this.bidListMapper = bidListMapper;
    }

    /**
     * Récupère l'ensemble des éléments.
     *
     * @return la liste de tous les {@link BidListModel} (jamais {@code null})
     */
    public List<BidListModel> findAll() {
        return bidListRepository.findAll().stream().map(bidListMapper::toModel).toList();
    }

    /**
     * Récupère un élément par son identifiant.
     *
     * @param id l'identifiant recherché
     * @return un {@link Optional} contenant le {@link BidListModel} s'il existe, vide sinon
     */
    public Optional<BidListModel> findById(Integer id) {
        return bidListRepository.findById(id).map(bidListMapper::toModel);
    }

    /**
     * Crée ou met à jour un élément.
     *
     * @param model le Model à persister
     * @return le {@link BidListModel} persisté (avec son identifiant renseigné)
     */
    public BidListModel save(BidListModel model) {
        return bidListMapper.toModel(bidListRepository.save(bidListMapper.toEntity(model)));
    }

    /**
     * Supprime un élément par son identifiant.
     *
     * @param id l'identifiant de l'élément à supprimer
     */
    public void deleteById(Integer id) {
        bidListRepository.deleteById(id);
    }
}
