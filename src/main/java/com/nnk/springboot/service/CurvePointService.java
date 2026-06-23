package com.nnk.springboot.service;

import com.nnk.springboot.mapper.CurvePointMapper;
import com.nnk.springboot.model.CurvePointModel;
import com.nnk.springboot.repository.CurvePointRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service métier pour les points de courbe.
 * <p>
 * Orchestre le {@link CurvePointRepository} et le {@link CurvePointMapper} : les méthodes
 * exposées travaillent avec des {@link CurvePointModel}, la conversion vers/depuis l'entité
 * {@link com.nnk.springboot.entity.CurvePointEntity} étant gérée en interne.
 */
@Service
public class CurvePointService {

    private final CurvePointRepository curvePointRepository;
    private final CurvePointMapper curvePointMapper;

    /**
     * Constructeur par défaut.
     *
     * @param curvePointRepository repository d'accès aux données, injecté par Spring
     * @param curvePointMapper convertisseur entité/Model, injecté par Spring
     */
    public CurvePointService(CurvePointRepository curvePointRepository, CurvePointMapper curvePointMapper) {
        this.curvePointRepository = curvePointRepository;
        this.curvePointMapper = curvePointMapper;
    }

    /**
     * Récupère l'ensemble des éléments.
     *
     * @return la liste de tous les {@link CurvePointModel} (jamais {@code null})
     */
    public List<CurvePointModel> findAll() {
        return curvePointRepository.findAll().stream().map(curvePointMapper::toModel).toList();
    }

    /**
     * Récupère un élément par son identifiant.
     *
     * @param id l'identifiant recherché
     * @return un {@link Optional} contenant le {@link CurvePointModel} s'il existe, vide sinon
     */
    public Optional<CurvePointModel> findById(Integer id) {
        return curvePointRepository.findById(id).map(curvePointMapper::toModel);
    }

    /**
     * Crée ou met à jour un élément.
     *
     * @param model le Model à persister
     * @return le {@link CurvePointModel} persisté (avec son identifiant renseigné)
     */
    public CurvePointModel save(CurvePointModel model) {
        return curvePointMapper.toModel(curvePointRepository.save(curvePointMapper.toEntity(model)));
    }

    /**
     * Supprime un élément par son identifiant.
     *
     * @param id l'identifiant de l'élément à supprimer
     */
    public void deleteById(Integer id) {
        curvePointRepository.deleteById(id);
    }
}
