package com.linkstart.api.service;

import com.linkstart.api.model.dto.DiscordMessageDto;
import com.linkstart.api.model.dto.DiscordUserDto;
import com.linkstart.api.model.dto.PlaylistDto;
import com.linkstart.api.model.entity.DiscordMessage;
import com.linkstart.api.model.entity.Playlist;
import com.linkstart.api.repo.DiscordMessageRepo;
import com.linkstart.api.repo.DiscordUserRepo;
import com.linkstart.api.repo.PlaylistRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscordMessageService {

    private final DiscordMessageRepo discordMessageRepo;
    private final ModelMapper modelMapper;

    public DiscordMessageService(DiscordMessageRepo discordMessageRepo, ModelMapper modelMapper) {
        this.discordMessageRepo = discordMessageRepo;
        this.modelMapper = modelMapper;
    }

    public List<DiscordMessageDto> getDiscordMessages() {
        List<DiscordMessage> discordMessages = discordMessageRepo.findAll();
        return discordMessages.stream()
                .map(discordMessage -> modelMapper.map(discordMessage, DiscordMessageDto.class))
                .toList();
    }
}
