package com.linkstart.backend.repo;

import com.linkstart.backend.model.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepo extends JpaRepository<Purchase, Long> {
    List<Purchase> findPurchasesByMember_Id(Long id);
}
