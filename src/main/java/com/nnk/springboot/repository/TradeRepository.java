package com.nnk.springboot.repository;

import com.nnk.springboot.entity.TradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository Spring Data JPA pour les transactions (trades) ({@link TradeEntity}).
 * <p>
 * Hérite des opérations CRUD standard de {@link JpaRepository}.
 */
public interface TradeRepository extends JpaRepository<TradeEntity, Integer> {
}
