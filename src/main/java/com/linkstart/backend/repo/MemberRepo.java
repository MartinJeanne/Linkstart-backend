package com.linkstart.backend.repo;

import com.linkstart.backend.model.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepo extends JpaRepository<Member, Long> {
    Optional<Member> findByDiscordId(String discordId);
    Page<Member> findByTagContaining(String filter, Pageable pageable);
}
