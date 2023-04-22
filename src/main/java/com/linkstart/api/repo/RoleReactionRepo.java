package com.linkstart.api.repo;

import com.linkstart.api.model.entity.DiscordMessage;
import com.linkstart.api.model.entity.RoleReaction;
import com.linkstart.api.model.entity.RoleReactionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleReactionRepo extends JpaRepository<RoleReaction, Long> {

    RoleReaction findByRoleReactionId(RoleReactionId reactionId);
}
