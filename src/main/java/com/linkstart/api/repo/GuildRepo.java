package com.linkstart.api.repo;

import com.linkstart.api.model.entity.Guild;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuildRepo extends JpaRepository<Guild, String> {
}
