package com.linkstart.api.repo;

import com.linkstart.api.model.entity.DiscordUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscordUserRepo extends JpaRepository<DiscordUser, Long> {
    Page<DiscordUser> findByTagContaining(String filter, Pageable pageable);
}
