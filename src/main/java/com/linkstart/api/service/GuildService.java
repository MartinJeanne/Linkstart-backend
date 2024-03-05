package com.linkstart.api.service;

import com.linkstart.api.exception.NotFoundException;
import com.linkstart.api.mapper.GuildMapper;
import com.linkstart.api.model.dto.GuildDto;
import com.linkstart.api.model.entity.*;
import com.linkstart.api.repo.GuildRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuildService {

    private final GuildRepo guildRepo;
    private final GuildMapper guildMapper;

    public GuildService(GuildRepo guildRepo, GuildMapper guildMapper) {
        this.guildRepo = guildRepo;
        this.guildMapper = guildMapper;
    }

    public List<GuildDto> getGuilds() {
        List<Guild> guilds = guildRepo.findAll();
        return guildMapper.toDtoList(guilds);
    }

    public GuildDto getGuildById(String id) {
        Guild guild = guildRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(id, Guild.class));
        return guildMapper.toDto(guild);
    }

    public GuildDto createGuild(GuildDto guildDto) {
        Guild guild = guildMapper.toEntity(guildDto);
        Guild savedGuild = guildRepo.save(guild);
        return guildMapper.toDto(savedGuild);
    }
}
