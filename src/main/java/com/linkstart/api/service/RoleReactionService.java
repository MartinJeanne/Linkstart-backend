package com.linkstart.api.service;

import com.linkstart.api.mapper.MessageMapper;
import com.linkstart.api.mapper.RoleReactionMapper;
import com.linkstart.api.model.dto.*;
import com.linkstart.api.model.entity.Message;
import com.linkstart.api.model.entity.RoleReaction;
import com.linkstart.api.model.entity.RoleReactionId;
import com.linkstart.api.repo.RoleReactionRepo;
import org.springframework.stereotype.Service;

@Service
public class RoleReactionService {

    private final RoleReactionRepo roleReactionRepo;
    private final MessageService messageService;
    private final RoleReactionMapper roleReactionMapper;
    private final MessageMapper messageMapper;

    public RoleReactionService(RoleReactionRepo roleReactionRepo, MessageService messageService,
                               RoleReactionMapper roleReactionMapper, MessageMapper messageMapper) {
        this.roleReactionRepo = roleReactionRepo;
        this.messageService = messageService;
        this.roleReactionMapper = roleReactionMapper;
        this.messageMapper = messageMapper;
    }

    public RoleReactionDto getRoleReaction(String id, String reaction) {
        RoleReaction roleReaction;
        if (reaction == null) return null;
        MessageDto messageDto = messageService.getMessageById(id);
        Message message = messageMapper.toEntity(messageDto);

        RoleReactionId reactionId = new RoleReactionId();
        reactionId.setReaction(reaction);
        reactionId.setMessage(message);

        roleReaction = roleReactionRepo.findByRoleReactionId(reactionId);

        return roleReactionMapper.toDto(roleReaction);
    }
}
