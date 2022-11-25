package com.linkstart.backend.repo;

import com.linkstart.backend.model.entity.Member;
import com.linkstart.backend.model.entity.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepo extends JpaRepository<Purchase, Long> {

}
