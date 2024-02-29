package com.linkstart.api.repo;

import com.linkstart.api.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepo extends JpaRepository<Member, String> {
}
