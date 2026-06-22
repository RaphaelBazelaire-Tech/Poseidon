package com.nnk.springboot.repository;

import com.nnk.springboot.entity.BidListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository Spring Data JPA pour les offres (bids) ({@link BidListEntity}).
 * <p>
 * Hérite des opérations CRUD standard de {@link JpaRepository}.
 */
public interface BidListRepository extends JpaRepository<BidListEntity ,Integer> {
}
