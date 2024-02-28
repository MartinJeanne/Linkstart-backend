package com.linkstart.api.repo;

import com.linkstart.api.model.entity.DiscordServer;
import com.linkstart.api.model.entity.DiscordUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiscordServerRepo extends JpaRepository<DiscordServer, Long> {
    Optional<DiscordServer> findByDiscordId(String discordId);
}
