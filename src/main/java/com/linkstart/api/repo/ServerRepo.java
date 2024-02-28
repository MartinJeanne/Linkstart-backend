package com.linkstart.api.repo;

import com.linkstart.api.model.entity.Server;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServerRepo extends JpaRepository<Server, Long> {
    Optional<Server> findByDiscordId(String discordId);
}
