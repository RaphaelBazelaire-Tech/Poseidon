package com.nnk.springboot.service;

import com.nnk.springboot.mapper.RatingMapper;
import com.nnk.springboot.model.RatingModel;
import com.nnk.springboot.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service métier pour les notations financières.
 * <p>
 * Orchestre le {@link RatingRepository} et le {@link RatingMapper} : les méthodes
 * exposées travaillent avec des {@link RatingModel}, la conversion vers/depuis l'entité
 * {@link com.nnk.springboot.entity.RatingEntity} étant gérée en interne.
 */
@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;

    /**
     * Constructeur par défaut.
     *
     * @param ratingRepository repository d'accès aux données, injecté par Spring
     * @param ratingMapper convertisseur entité/Model, injecté par Spring
     */
    public RatingService(RatingRepository ratingRepository, RatingMapper ratingMapper) {
        this.ratingRepository = ratingRepository;
        this.ratingMapper = ratingMapper;
    }

    /**
     * Récupère l'ensemble des éléments.
     *
     * @return la liste de tous les {@link RatingModel} (jamais {@code null})
     */
    public List<RatingModel> findAll() {
        return ratingRepository.findAll().stream().map(ratingMapper::toModel).toList();
    }

    /**
     * Récupère un élément par son identifiant.
     *
     * @param id l'identifiant recherché
     * @return un {@link Optional} contenant le {@link RatingModel} s'il existe, vide sinon
     */
    public Optional<RatingModel> findById(Integer id) {
        return ratingRepository.findById(id).map(ratingMapper::toModel);
    }

    /**
     * Crée ou met à jour un élément.
     *
     * @param model le Model à persister
     * @return le {@link RatingModel} persisté (avec son identifiant renseigné)
     */
    public RatingModel save(RatingModel model) {
        return ratingMapper.toModel(ratingRepository.save(ratingMapper.toEntity(model)));
    }

    /**
     * Supprime un élément par son identifiant.
     *
     * @param id l'identifiant de l'élément à supprimer
     */
    public void deleteById(Integer id) {
        ratingRepository.deleteById(id);
    }
}
