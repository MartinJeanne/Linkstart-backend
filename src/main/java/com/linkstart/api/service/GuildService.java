package com.linkstart.api.service;

import com.linkstart.api.exception.NotFoundException;
import com.linkstart.api.model.dto.GuildDto;
import com.linkstart.api.model.entity.*;
import com.linkstart.api.repo.GuildRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GuildService {

    private final GuildRepo guildRepo;
    private final ModelMapper modelMapper;

    public GuildService(GuildRepo guildRepo, ModelMapper modelMapper) {
        this.guildRepo = guildRepo;
        this.modelMapper = modelMapper;
    }

    public List<GuildDto> getServers() {
        List<Guild> guilds = guildRepo.findAll();
        return guilds
                .stream()
                .map(guild -> modelMapper.map(guild, GuildDto.class))
                .toList();
    }

    public GuildDto getServerById(Long id) {
        Optional<Guild> discordServer = guildRepo.findById(id);
        if(discordServer.isEmpty())
            throw new NotFoundException(id + " Guild");

        return modelMapper.map(discordServer.get(), GuildDto.class);
    }

    public GuildDto createServer(GuildDto guildDto) {
        Guild guild = modelMapper.map(guildDto, Guild.class);
        Guild savedGuild = guildRepo.save(guild);
        return modelMapper.map(savedGuild, GuildDto.class);
    }
}
