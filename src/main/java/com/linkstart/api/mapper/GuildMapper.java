package com.linkstart.api.mapper;

import com.linkstart.api.exception.NotFoundException;
import com.linkstart.api.model.dto.GuildDto;
import com.linkstart.api.model.entity.Guild;
import com.linkstart.api.model.entity.Member;
import com.linkstart.api.repo.MemberRepo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class GuildMapper {

    @Autowired
    protected MemberRepo memberRepo;

    @Mapping(target = "membersId", source = "members", qualifiedByName = "membersIdToIds")
    public abstract GuildDto toDto(Guild guild);

    public List<GuildDto> toDtoList(List<Guild> guilds) {
        List<GuildDto> guildsDto = new ArrayList<>();
        for (Guild guild : guilds) {
            guildsDto.add(this.toDto(guild));
        }
        return guildsDto;
    }

    @Mapping(target = "members", source = "membersId", qualifiedByName = "idsToMembers")
    public abstract Guild toEntity(GuildDto guildDto);

    @Named("membersIdToIds")
    public List<String> membersIdToIds(List<Member> members) {
        if (members == null || members.isEmpty()) return Collections.emptyList();

        return members.stream()
                .map(Member::getId)
                .toList();
    }

    @Named("idsToMembers")
    public List<Member> idsToMembers(List<String> ids) {
        if (ids == null || ids.isEmpty()) return Collections.emptyList();

        Member member;
        List<Member> members = new ArrayList<>();
        for (String memberId : ids) {
            member = memberRepo.findById(memberId).orElseThrow(() -> new NotFoundException(memberId, Member.class));
            members.add(member);
        }
        return members;
    }
}
