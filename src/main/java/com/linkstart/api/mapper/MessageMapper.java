package com.linkstart.api.mapper;

import com.linkstart.api.model.dto.MemberDto;
import com.linkstart.api.model.dto.MessageDto;
import com.linkstart.api.model.dto.RoleReactionDto;
import com.linkstart.api.model.entity.Guild;
import com.linkstart.api.model.entity.Member;
import com.linkstart.api.model.entity.Message;
import com.linkstart.api.model.entity.RoleReaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class MessageMapper {

    public abstract MessageDto toDto(Message message);
    public abstract Message toEntity(MessageDto messageDto);

    public List<MessageDto> toDtoList(List<Message> messages) {
        List<MessageDto> messagesDto = new ArrayList<>();
        for(Message message : messages) {
            messagesDto.add(this.toDto(message));
        }
        return messagesDto;
    }
}
