package com.linkstart.api.repo;

import com.linkstart.api.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message, Long> {
}
