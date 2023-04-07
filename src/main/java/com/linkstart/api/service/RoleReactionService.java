package com.linkstart.api.service;

import com.linkstart.api.model.dto.DiscordMessageDto;
import com.linkstart.api.model.dto.DiscordUserDto;
import com.linkstart.api.model.dto.PlaylistDto;
import com.linkstart.api.model.dto.RoleReactionDto;
import com.linkstart.api.model.entity.DiscordMessage;
import com.linkstart.api.model.entity.RoleReaction;
import com.linkstart.api.repo.DiscordMessageRepo;
import com.linkstart.api.repo.RoleReactionRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleReactionService {

    private final RoleReactionRepo roleReactionRepo;
    private final ModelMapper modelMapper;

    public RoleReactionService(RoleReactionRepo roleReactionRepo, ModelMapper modelMapper) {
        this.roleReactionRepo = roleReactionRepo;
        this.modelMapper = modelMapper;
    }

    public List<RoleReactionDto> getRoleReactions() {
        List<RoleReaction> roleReactions = roleReactionRepo.findAll();
        return roleReactions.stream().map(roleReaction -> {
            RoleReactionDto r = modelMapper.map(roleReaction, RoleReactionDto.class);
            r.setDiscordMessage(modelMapper.map(roleReaction.getDiscordMessage(), DiscordMessageDto.class));
            return r;
        }).toList();
    }
}
