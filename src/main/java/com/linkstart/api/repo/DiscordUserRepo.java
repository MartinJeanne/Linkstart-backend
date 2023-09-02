package com.linkstart.api.repo;

import com.linkstart.api.model.entity.DiscordUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiscordUserRepo extends JpaRepository<DiscordUser, Long> {
    Optional<DiscordUser> findByDiscordId(String discordId);
}
