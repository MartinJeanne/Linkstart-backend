package com.linkstart.backend.repo;

import com.linkstart.backend.model.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepo extends JpaRepository<Member, Long> {
    Page<Member> findByUsernameContaining(String filter, Pageable pageable);
}
