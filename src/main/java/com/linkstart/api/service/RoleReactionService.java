package com.linkstart.api.service;

import com.linkstart.api.model.dto.*;
import com.linkstart.api.model.entity.DiscordMessage;
import com.linkstart.api.model.entity.RoleReaction;
import com.linkstart.api.model.entity.RoleReactionId;
import com.linkstart.api.repo.DiscordMessageRepo;
import com.linkstart.api.repo.RoleReactionRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleReactionService {

    private final RoleReactionRepo roleReactionRepo;
    private final DiscordMessageService discordMessageService;
    private final ModelMapper modelMapper;

    public RoleReactionService(RoleReactionRepo roleReactionRepo, DiscordMessageService discordMessageService,
            ModelMapper modelMapper) {
        this.roleReactionRepo = roleReactionRepo;
        this.discordMessageService = discordMessageService;
        this.modelMapper = modelMapper;
    }

    public RoleReactionDto getRoleReaction(String discordId, String reaction) {
        RoleReaction roleReaction;
        if (discordId == null || reaction == null) return null;
        DiscordMessageDto discordMessageDto = discordMessageService.getDiscordMessageByDiscordId(discordId);
        DiscordMessage discordMessage = modelMapper.map(discordMessageDto, DiscordMessage.class);

        RoleReactionId reactionId = new RoleReactionId();
        reactionId.setReaction(reaction);
        reactionId.setDiscordMessage(discordMessage);

        roleReaction = roleReactionRepo.findByRoleReactionId(reactionId);

        return modelMapper.map(roleReaction, RoleReactionDto.class);
    }
}
