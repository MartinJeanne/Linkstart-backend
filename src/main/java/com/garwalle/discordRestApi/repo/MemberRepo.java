package com.garwalle.discordRestApi.repo;

import com.garwalle.discordRestApi.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepo extends JpaRepository<Member, Long> {
    List<Member> findByUsernameContaining(String username);
}
