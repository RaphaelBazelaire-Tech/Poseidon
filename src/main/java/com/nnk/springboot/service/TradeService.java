package com.nnk.springboot.service;

import com.nnk.springboot.mapper.TradeMapper;
import com.nnk.springboot.model.TradeModel;
import com.nnk.springboot.repository.TradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service métier pour les transactions (trades).
 * <p>
 * Orchestre le {@link TradeRepository} et le {@link TradeMapper} : les méthodes
 * exposées travaillent avec des {@link TradeModel}, la conversion vers/depuis l'entité
 * {@link com.nnk.springboot.entity.TradeEntity} étant gérée en interne.
 */
@Service
public class TradeService {

    private final TradeRepository tradeRepository;
    private final TradeMapper tradeMapper;

    /**
     * @param tradeRepository repository d'accès aux données, injecté par Spring
     * @param tradeMapper convertisseur entité/Model, injecté par Spring
     */
    public TradeService(TradeRepository tradeRepository, TradeMapper tradeMapper) {
        this.tradeRepository = tradeRepository;
        this.tradeMapper = tradeMapper;
    }

    /**
     * Récupère l'ensemble des éléments.
     *
     * @return la liste de tous les {@link TradeModel} (jamais {@code null})
     */
    public List<TradeModel> findAll() {
        return tradeRepository.findAll().stream().map(tradeMapper::toModel).toList();
    }

    /**
     * Récupère un élément par son identifiant.
     *
     * @param id l'identifiant recherché
     * @return un {@link Optional} contenant le {@link TradeModel} s'il existe, vide sinon
     */
    public Optional<TradeModel> findById(Integer id) {
        return tradeRepository.findById(id).map(tradeMapper::toModel);
    }

    /**
     * Crée ou met à jour un élément.
     *
     * @param model le Model à persister
     * @return le {@link TradeModel} persisté (avec son identifiant renseigné)
     */
    public TradeModel save(TradeModel model) {
        return tradeMapper.toModel(tradeRepository.save(tradeMapper.toEntity(model)));
    }

    /**
     * Supprime un élément par son identifiant.
     *
     * @param id l'identifiant de l'élément à supprimer
     */
    public void deleteById(Integer id) {
        tradeRepository.deleteById(id);
    }
}
