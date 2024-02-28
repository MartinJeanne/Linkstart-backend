package com.linkstart.api.repo;

import com.linkstart.api.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepo extends JpaRepository<Member, Long> {
    Optional<Member> findByDiscordId(String discordId);
}
