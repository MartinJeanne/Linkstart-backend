package com.linkstart.api.repo;

import com.linkstart.api.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepo extends JpaRepository<Client, Integer> {
    Optional<Client> findByClientName(String username);
    Boolean existsByClientName(String username);
}
