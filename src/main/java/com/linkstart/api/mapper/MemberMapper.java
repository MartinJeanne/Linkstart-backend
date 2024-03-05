package com.linkstart.api.mapper;

import com.linkstart.api.model.dto.MemberDto;
import com.linkstart.api.model.entity.Guild;
import com.linkstart.api.model.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class MemberMapper {

    @Mapping(target = "guildsId", source = "guilds",qualifiedByName = "guildsToIds")
    public abstract MemberDto toDto(Member member);

    public List<MemberDto> toDtoList(List<Member> members) {
        List<MemberDto> membersDto = new ArrayList<>();
        for(Member member : members) {
            membersDto.add(this.toDto(member));
        }
        return membersDto;
    }
    @Mapping(target = "guilds", ignore = true)
    public abstract Member toEntity(MemberDto member);

    @Named("guildsToIds")
    public List<String> guildsToIds(List<Guild> guilds) {
        if (guilds == null || guilds.isEmpty()) return Collections.emptyList();

        return guilds.stream()
                .map(Guild::getId)
                .toList();
    }
}
