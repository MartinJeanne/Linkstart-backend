package com.linkstart.api.repo;

import com.linkstart.api.model.entity.Member;
import com.linkstart.api.model.entity.Playlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepo extends JpaRepository<Playlist, Integer> {
    List<Playlist> findByMember(Member member);
    Page<Playlist> findByNameContaining(String name, Pageable pageable);
}
