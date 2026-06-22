package com.nnk.springboot.service;

import com.nnk.springboot.mapper.UserMapper;
import com.nnk.springboot.model.UserModel;
import com.nnk.springboot.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service métier pour les utilisateurs.
 * <p>
 * Orchestre le {@link UserRepository} et le {@link UserMapper} : les méthodes
 * exposées travaillent avec des {@link UserModel}, la conversion vers/depuis l'entité
 * {@link com.nnk.springboot.entity.UserEntity} étant gérée en interne.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * @param userRepository repository d'accès aux données, injecté par Spring
     * @param userMapper convertisseur entité/Model, injecté par Spring
     */
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    /**
     * Récupère l'ensemble des éléments.
     *
     * @return la liste de tous les {@link UserModel} (jamais {@code null})
     */
    public List<UserModel> findAll() {
        return userRepository.findAll().stream().map(userMapper::toModel).toList();
    }

    /**
     * Récupère un élément par son identifiant.
     *
     * @param id l'identifiant recherché
     * @return un {@link Optional} contenant le {@link UserModel} s'il existe, vide sinon
     */
    public Optional<UserModel> findById(Integer id) {
        return userRepository.findById(id).map(userMapper::toModel);
    }

    /**
     * Crée ou met à jour un élément.
     *
     * @param model le Model à persister
     * @return le {@link UserModel} persisté (avec son identifiant renseigné)
     */
    public UserModel save(UserModel model) {
        return userMapper.toModel(userRepository.save(userMapper.toEntity(model)));
    }

    /**
     * Supprime un élément par son identifiant.
     *
     * @param id l'identifiant de l'élément à supprimer
     */
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }
}
