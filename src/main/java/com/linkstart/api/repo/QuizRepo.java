package com.linkstart.api.repo;

import com.linkstart.api.model.entity.DiscordUser;
import com.linkstart.api.model.entity.Playlist;
import com.linkstart.api.model.entity.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepo extends JpaRepository<Quiz, Long> {
    List<Quiz> findByDiscordUser(DiscordUser discordUser);
}
