package com.linkstart.api.mapper;

import com.linkstart.api.exception.NotFoundException;
import com.linkstart.api.model.dto.MemberDto;
import com.linkstart.api.model.entity.Guild;
import com.linkstart.api.model.entity.Member;
import com.linkstart.api.repo.GuildRepo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class MemberMapper {

    @Autowired
    protected GuildRepo guildRepo;

    @Mapping(target = "guildsId", source = "guilds",qualifiedByName = "guildsToIds")
    public abstract MemberDto toDto(Member member);

    public List<MemberDto> toDtoList(List<Member> members) {
        List<MemberDto> membersDto = new ArrayList<>();
        for(Member member : members) {
            membersDto.add(this.toDto(member));
        }
        return membersDto;
    }
    @Mapping(target = "guilds", source = "guildsId",qualifiedByName = "idsToGuilds")
    public abstract Member toEntity(MemberDto member);

    @Named("guildsToIds")
    public List<String> guildsToIds(List<Guild> guilds) {
        if (guilds == null || guilds.isEmpty()) return Collections.emptyList();

        return guilds.stream()
                .map(Guild::getId)
                .toList();
    }

    @Named("idsToGuilds")
    public List<Guild> idsToGuilds(List<String> ids) {
        if (ids == null || ids.isEmpty()) return Collections.emptyList();

        Guild guild;
        List<Guild> guilds = new ArrayList<>();
        for (String guildId : ids) {
            guild = guildRepo.findById(guildId).orElseThrow(() -> new NotFoundException(guildId, Guild.class));
            guilds.add(guild);
        }
        return guilds;
    }
}
