package com.linkstart.api.service;

import com.linkstart.api.model.dto.MessageDto;
import com.linkstart.api.model.entity.Message;
import com.linkstart.api.repo.MessageRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepo messageRepo;
    private final ModelMapper modelMapper;

    public MessageService(MessageRepo messageRepo, ModelMapper modelMapper) {
        this.messageRepo = messageRepo;
        this.modelMapper = modelMapper;
    }

    public List<MessageDto> getMessages() {
        List<Message> messages = messageRepo.findAll();
        return messages.stream()
                .map(discordMessage -> modelMapper.map(discordMessage, MessageDto.class))
                .toList();
    }

    public MessageDto getMessageByDiscordId(String discordId) {
        Message message = messageRepo.findByDiscordId(discordId);
        return modelMapper.map(message, MessageDto.class);
    }
}
