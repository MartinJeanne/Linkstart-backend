package com.linkstart.backend.repo;

import com.linkstart.backend.model.entity.DiscordUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscordUserRepo extends JpaRepository<DiscordUser, Long> {
    Page<DiscordUser> findByTagContaining(String filter, Pageable pageable);
}
