package com.nnk.springboot.repository;

import com.nnk.springboot.entity.RuleNameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository Spring Data JPA pour les règles métier ({@link RuleNameEntity}).
 * <p>
 * Hérite des opérations CRUD standard de {@link JpaRepository}.
 */
public interface RuleNameRepository extends JpaRepository<RuleNameEntity, Integer> {
}
