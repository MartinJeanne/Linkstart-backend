package com.linkstart.api.repo;

import com.linkstart.api.model.entity.RoleReaction;
import com.linkstart.api.model.entity.RoleReactionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleReactionRepo extends JpaRepository<RoleReaction, String> {

    RoleReaction findByRoleReactionId(RoleReactionId reactionId);
}
