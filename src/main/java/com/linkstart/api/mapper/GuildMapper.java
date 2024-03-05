package com.linkstart.api.mapper;

import com.linkstart.api.model.dto.GuildDto;
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
public abstract class GuildMapper {

    @Mapping(target = "membersId", source = "members", qualifiedByName = "membersIdToIds")
    public abstract GuildDto toDto(Guild guild);

    public List<GuildDto> toDtoList(List<Guild> guilds) {
        List<GuildDto> guildsDto = new ArrayList<>();
        for (Guild guild : guilds) {
            guildsDto.add(this.toDto(guild));
        }
        return guildsDto;
    }

    @Mapping(target = "members", ignore = true)
    public abstract Guild toEntity(GuildDto guildDto);

    @Named("membersIdToIds")
    public List<String> membersIdToIds(List<Member> members) {
        if (members == null || members.isEmpty()) return Collections.emptyList();

        return members.stream()
                .map(Member::getId)
                .toList();
    }
}
