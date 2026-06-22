package com.nnk.springboot.repository;

import com.nnk.springboot.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository Spring Data JPA pour les notations financières ({@link RatingEntity}).
 * <p>
 * Hérite des opérations CRUD standard de {@link JpaRepository}.
 */
public interface RatingRepository extends JpaRepository<RatingEntity, Integer> {
}
