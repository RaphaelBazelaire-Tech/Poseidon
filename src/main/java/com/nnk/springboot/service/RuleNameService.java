package com.nnk.springboot.service;

import com.nnk.springboot.mapper.RuleNameMapper;
import com.nnk.springboot.model.RuleNameModel;
import com.nnk.springboot.repository.RuleNameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service métier pour les règles métier.
 * <p>
 * Orchestre le {@link RuleNameRepository} et le {@link RuleNameMapper} : les méthodes
 * exposées travaillent avec des {@link RuleNameModel}, la conversion vers/depuis l'entité
 * {@link com.nnk.springboot.entity.RuleNameEntity} étant gérée en interne.
 */
@Service
public class RuleNameService {

    private final RuleNameRepository ruleNameRepository;
    private final RuleNameMapper ruleNameMapper;

    /**
     * @param ruleNameRepository repository d'accès aux données, injecté par Spring
     * @param ruleNameMapper convertisseur entité/Model, injecté par Spring
     */
    public RuleNameService(RuleNameRepository ruleNameRepository, RuleNameMapper ruleNameMapper) {
        this.ruleNameRepository = ruleNameRepository;
        this.ruleNameMapper = ruleNameMapper;
    }

    /**
     * Récupère l'ensemble des éléments.
     *
     * @return la liste de tous les {@link RuleNameModel} (jamais {@code null})
     */
    public List<RuleNameModel> findAll() {
        return ruleNameRepository.findAll().stream().map(ruleNameMapper::toModel).toList();
    }

    /**
     * Récupère un élément par son identifiant.
     *
     * @param id l'identifiant recherché
     * @return un {@link Optional} contenant le {@link RuleNameModel} s'il existe, vide sinon
     */
    public Optional<RuleNameModel> findById(Integer id) {
        return ruleNameRepository.findById(id).map(ruleNameMapper::toModel);
    }

    /**
     * Crée ou met à jour un élément.
     *
     * @param model le Model à persister
     * @return le {@link RuleNameModel} persisté (avec son identifiant renseigné)
     */
    public RuleNameModel save(RuleNameModel model) {
        return ruleNameMapper.toModel(ruleNameRepository.save(ruleNameMapper.toEntity(model)));
    }

    /**
     * Supprime un élément par son identifiant.
     *
     * @param id l'identifiant de l'élément à supprimer
     */
    public void deleteById(Integer id) {
        ruleNameRepository.deleteById(id);
    }
}
