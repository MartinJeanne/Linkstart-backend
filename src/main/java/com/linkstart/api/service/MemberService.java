package com.linkstart.api.service;

import com.linkstart.api.exception.NotFoundException;
import com.linkstart.api.mapper.MemberMapper;
import com.linkstart.api.model.dto.PlaylistDto;
import com.linkstart.api.model.entity.Guild;
import com.linkstart.api.model.entity.Member;
import com.linkstart.api.model.dto.MemberDto;
import com.linkstart.api.repo.GuildRepo;
import com.linkstart.api.repo.MemberRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MemberService {
    private final MemberRepo memberRepo;
    private final PlaylistService playlistService;
    private final GuildRepo guildRepo;
    private final MemberMapper memberMapper;

    public MemberService(
            MemberRepo memberRepo,
            PlaylistService playlistService,
            MemberMapper memberMapper,
            GuildRepo guildRepo) {
        this.memberRepo = memberRepo;
        this.playlistService = playlistService;
        this.memberMapper = memberMapper;
        this.guildRepo = guildRepo;
    }

    public List<MemberDto> getMembers() {
        List<Member> members = memberRepo.findAll();
        return memberMapper.toDtoList(members);
    }

    public List<MemberDto> birthdayIsToday() {
        List<MemberDto> members = getMembers();
        LocalDate today = LocalDate.now();

        List<MemberDto> discordUsersBirthdayIsNow = new ArrayList<>();
        for (MemberDto member : members) {
            if (member.getBirthday() == null) continue;

            LocalDate userBirthday = member.getBirthday();
            // To compare dates, we set them to the same year
            userBirthday = userBirthday.withYear(today.getYear());

            if (userBirthday.equals(today)) {
                discordUsersBirthdayIsNow.add(member);
            }
        }
        return discordUsersBirthdayIsNow;
    }

    public MemberDto getMemberById(String id) {
        Member member = memberRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(id, Member.class));

        return memberMapper.toDto(member);
    }

    public MemberDto createMember(MemberDto memberDto) {
        List<Guild> guilds = new ArrayList<>();
        Guild guild;
        for (String id : memberDto.getGuildsId()) {
            guild = guildRepo.findById(id).orElseThrow(() -> new NotFoundException(id, Guild.class));
            guilds.add(guild);
        }

        Member member = memberMapper.toEntity(memberDto);
        member.setGuilds(guilds);

        memberRepo.save(member);
        return memberMapper.toDto(member);
    }

    public MemberDto updateMember(String id, MemberDto memberDto) {
        memberRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(id, Member.class));

        List<Guild> guilds = new ArrayList<>();
        Guild guild;
        for (String guildId : memberDto.getGuildsId()) {
            guild = guildRepo.findById(guildId).orElseThrow(() -> new NotFoundException(guildId, Guild.class));
            guilds.add(guild);
        }

        Member memberUpdate = memberMapper.toEntity(memberDto);
        memberUpdate.setGuilds(guilds);
        Member updatedMember = memberRepo.save(memberUpdate);

        return memberMapper.toDto(updatedMember);
    }

    public void deleteMember(String id) {
        Member member = memberRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(id, Member.class));

        memberRepo.delete(member);
    }

    public List<PlaylistDto> getPlaylistsByMember(String id) {
        Member member = memberRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(id, Member.class));

        return playlistService.getPlaylistsByDiscordUser(member);
    }
}
