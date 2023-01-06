package com.linkstart.backend.repo;

import com.linkstart.backend.model.entity.Playlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepo extends JpaRepository<Playlist, Long> {
    List<Playlist> findByMember_Id(Long id);
    List<Playlist> findByMember_DiscordId(String id);
    Page<Playlist> findByNameContaining(String filter, Pageable pageable);
}
