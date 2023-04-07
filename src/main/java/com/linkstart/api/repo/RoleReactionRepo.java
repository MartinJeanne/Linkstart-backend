package com.linkstart.api.repo;

import com.linkstart.api.model.entity.DiscordMessage;
import com.linkstart.api.model.entity.RoleReaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleReactionRepo extends JpaRepository<RoleReaction, Long> {
}
