package com.linkstart.api.service;

import com.linkstart.api.exception.NotFoundException;
import com.linkstart.api.mapper.MessageMapper;
import com.linkstart.api.model.dto.MessageDto;
import com.linkstart.api.model.entity.Message;
import com.linkstart.api.repo.MessageRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepo messageRepo;
    private final MessageMapper messageMapper;

    public MessageService(MessageRepo messageRepo, MessageMapper messageMapper) {
        this.messageRepo = messageRepo;
        this.messageMapper = messageMapper;
    }

    public List<MessageDto> getMessages() {
        List<Message> messages = messageRepo.findAll();
        return messageMapper.toDtoList(messages);
    }

    public MessageDto getMessageById(String id) {
        Message message = messageRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(id, Message.class));
        return messageMapper.toDto(message);
    }
}
