package com.linkstart.api.mapper;

import com.linkstart.api.model.dto.MemberDto;
import com.linkstart.api.model.dto.RoleReactionDto;
import com.linkstart.api.model.entity.Guild;
import com.linkstart.api.model.entity.Member;
import com.linkstart.api.model.entity.RoleReaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class RoleReactionMapper {

    public abstract RoleReactionDto toDto(RoleReaction roleReaction);
    public abstract RoleReaction toEntity(RoleReactionDto roleReactionDto);
}
