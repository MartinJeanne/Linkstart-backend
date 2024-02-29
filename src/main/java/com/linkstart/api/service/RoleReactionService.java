package com.linkstart.api.service;

import com.linkstart.api.model.dto.*;
import com.linkstart.api.model.entity.Message;
import com.linkstart.api.model.entity.RoleReaction;
import com.linkstart.api.model.entity.RoleReactionId;
import com.linkstart.api.repo.RoleReactionRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class RoleReactionService {

    private final RoleReactionRepo roleReactionRepo;
    private final MessageService messageService;
    private final ModelMapper modelMapper;

    public RoleReactionService(RoleReactionRepo roleReactionRepo, MessageService messageService,
            ModelMapper modelMapper) {
        this.roleReactionRepo = roleReactionRepo;
        this.messageService = messageService;
        this.modelMapper = modelMapper;
    }

    public RoleReactionDto getRoleReaction(long id, String reaction) {
        RoleReaction roleReaction;
        if (reaction == null) return null;
        MessageDto messageDto = messageService.getMessageByDiscordId(id);
        Message message = modelMapper.map(messageDto, Message.class);

        RoleReactionId reactionId = new RoleReactionId();
        reactionId.setReaction(reaction);
        reactionId.setMessage(message);

        roleReaction = roleReactionRepo.findByRoleReactionId(reactionId);

        return modelMapper.map(roleReaction, RoleReactionDto.class);
    }
}
