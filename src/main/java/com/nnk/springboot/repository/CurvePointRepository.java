package com.nnk.springboot.repository;

import com.nnk.springboot.entity.CurvePointEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository Spring Data JPA pour les points de courbe ({@link CurvePointEntity}).
 * <p>
 * Hérite des opérations CRUD standard de {@link JpaRepository}.
 */
public interface CurvePointRepository extends JpaRepository<CurvePointEntity, Integer> {
}
