package com.linkstart.api.service;

import com.linkstart.api.exception.NotFoundException;
import com.linkstart.api.mapper.MemberMapper;
import com.linkstart.api.model.dto.MemberDto;
import com.linkstart.api.model.dto.PlaylistDto;
import com.linkstart.api.model.entity.Member;
import com.linkstart.api.repo.GuildRepo;
import com.linkstart.api.repo.MemberRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        Member member = memberMapper.toEntity(memberDto);
        Member savedMember = memberRepo.save(member);
        return memberMapper.toDto(savedMember);
    }

    public MemberDto updateMember(String id, MemberDto memberDto) {
        memberRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(id, Member.class));

        Member memberUpdate = memberMapper.toEntity(memberDto);
        Member updatedMember = memberRepo.save(memberUpdate);
        return memberMapper.toDto(updatedMember);
    }

    public MemberDto partiallyUpdateMember(String id, MemberDto memberDto) throws IllegalAccessException {
        Member existingMember = memberRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(id, Member.class));

        Member incompleteMember = memberMapper.toEntity(memberDto);

        Class<?> memberClass = Member.class;
        Field[] memberFields = memberClass.getDeclaredFields();
        for (Field field : memberFields) {
            field.setAccessible(true);

            Object value = field.get(incompleteMember);
            if (value != null) {
                if (!(value instanceof Collection<?>) || !((Collection<?>) value).isEmpty()) {
                    field.set(existingMember, value);
                }
            }
            field.setAccessible(false);
        }

        Member updatedMember = memberRepo.save(existingMember);
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
