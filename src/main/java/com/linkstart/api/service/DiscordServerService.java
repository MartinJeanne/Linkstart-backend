package com.linkstart.api.service;

import com.linkstart.api.exception.NoContentException;
import com.linkstart.api.exception.NotFoundException;
import com.linkstart.api.model.dto.DiscordMessageDto;
import com.linkstart.api.model.dto.DiscordServerDto;
import com.linkstart.api.model.dto.DiscordUserDto;
import com.linkstart.api.model.dto.RoleReactionDto;
import com.linkstart.api.model.entity.*;
import com.linkstart.api.repo.DiscordServerRepo;
import com.linkstart.api.repo.RoleReactionRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscordServerService {

    private final DiscordServerRepo discordServerRepo;
    private final ModelMapper modelMapper;

    public DiscordServerService(DiscordServerRepo discordServerRepo, ModelMapper modelMapper) {
        this.discordServerRepo = discordServerRepo;
        this.modelMapper = modelMapper;
    }

    public List<DiscordServerDto> getDiscordServers() {
        List<DiscordServer> discordServers = discordServerRepo.findAll();
        return discordServers
                .stream()
                .map(discordServer -> modelMapper.map(discordServer, DiscordServerDto.class))
                .toList();
    }

    public DiscordServerDto getDiscordServerById(Long id) {
        Optional<DiscordServer> discordServer = discordServerRepo.findById(id);
        if(discordServer.isEmpty())
            throw new NotFoundException(id + " DiscordServer");

        return modelMapper.map(discordServer.get(), DiscordServerDto.class);
    }

    public DiscordServerDto createDiscordServer(DiscordServerDto discordServerDto) {
        DiscordServer discordServer = modelMapper.map(discordServerDto, DiscordServer.class);
        DiscordServer savedDiscordServer =discordServerRepo.save(discordServer);
        return modelMapper.map(savedDiscordServer, DiscordServerDto.class);
    }
}
