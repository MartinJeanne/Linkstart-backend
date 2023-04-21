package com.linkstart.api.repo;

import com.linkstart.api.model.entity.DiscordMessage;
import com.linkstart.api.model.entity.DiscordUser;
import com.linkstart.api.model.entity.Playlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscordMessageRepo extends JpaRepository<DiscordMessage, Long> {
    DiscordMessage findByDiscordId(String discordId);
}
