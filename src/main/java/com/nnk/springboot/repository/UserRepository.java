package com.nnk.springboot.repository;

import com.nnk.springboot.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * Repository Spring Data JPA pour les utilisateurs ({@link UserEntity}).
 * <p>
 * Fournit les opérations CRUD standard ainsi qu'une recherche par nom
 * d'utilisateur, utilisée notamment lors de l'authentification.
 */
public interface UserRepository extends JpaRepository<UserEntity, Integer>, JpaSpecificationExecutor<UserEntity> {

    /**
     * Recherche un utilisateur par son nom d'utilisateur.
     *
     * @param username le nom d'utilisateur recherché
     * @return un {@link Optional} contenant l'utilisateur s'il existe, vide sinon
     */
    Optional<UserEntity> findByUsername(String username);
}
