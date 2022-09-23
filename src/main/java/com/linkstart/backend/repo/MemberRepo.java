package com.linkstart.backend.repo;

import com.linkstart.backend.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepo extends JpaRepository<Member, Long> {
    List<Member> findByUsernameContaining(String username);
}
