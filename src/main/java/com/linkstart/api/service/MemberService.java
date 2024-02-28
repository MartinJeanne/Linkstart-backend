package com.linkstart.api.service;

import com.linkstart.api.exception.NoContentException;
import com.linkstart.api.exception.NotFoundException;
import com.linkstart.api.model.dto.PlaylistDto;
import com.linkstart.api.model.entity.Server;
import com.linkstart.api.model.entity.Member;
import com.linkstart.api.model.dto.MemberDto;
import com.linkstart.api.repo.ServerRepo;
import com.linkstart.api.repo.MemberRepo;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final ServerRepo serverRepo;
    private final ModelMapper modelMapper;

    public MemberService(
            MemberRepo memberRepo,
            PlaylistService playlistService,
            ModelMapper modelMapper,
            ServerRepo serverRepo) {
        this.memberRepo = memberRepo;
        this.playlistService = playlistService;
        this.modelMapper = modelMapper;
        this.serverRepo = serverRepo;
    }

    public List<MemberDto> getMember() {
        List<Member> members = memberRepo.findAll();
        return members
                .stream()
                .map(user -> modelMapper.map(user, MemberDto.class))
                .toList();
    }

    public List<MemberDto> birthdayIsToday() {
        List<MemberDto> discordUsers = getMember();
        LocalDate today = LocalDate.now();

        List<MemberDto> discordUsersBirthdayIsNow = new ArrayList<>();
        for (MemberDto user : discordUsers) {
            if (user.getBirthday() == null) continue;

            LocalDate userBirthday = user.getBirthday();
            // To compare dates, we set them to the same year
            userBirthday = userBirthday.withYear(today.getYear());

            if (userBirthday.equals(today)) {
                discordUsersBirthdayIsNow.add(user);
            }
        }
        return discordUsersBirthdayIsNow;
    }

    public MemberDto getMemberByDiscordId(String id) {
        Member member = memberRepo.findByDiscordId(id).orElseThrow(NoContentException::new);
        return modelMapper.map(member, MemberDto.class);
    }

    public MemberDto createMember(MemberDto memberDto) {
        Optional<Server> discordServer = serverRepo.findByDiscordId(memberDto.getDiscordServerId());
        if (discordServer.isEmpty())
            throw new NotFoundException(memberDto.getDiscordServerId()+ " DiscordServerId");

        Member member = modelMapper.map(memberDto, Member.class);

        memberRepo.save(member);
        return modelMapper.map(member, MemberDto.class);
    }

    public MemberDto updateMember(Long id, MemberDto memberDto) {
        memberRepo.findById(id).orElseThrow(NoContentException::new);
        Member updatedMember = memberRepo.save(modelMapper.map(memberDto, Member.class));
        //updatedMember.setBirthday(LocalDate.of(2023, 11, 23));
        return modelMapper.map(updatedMember, MemberDto.class);
    }

    public ResponseEntity<HttpStatus> deleteMember(Long id) {
        Member member = memberRepo.findById(id).orElseThrow(NoContentException::new);
        memberRepo.delete(member);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public List<PlaylistDto> getPlaylistsByMember(String id) {
        MemberDto memberDto = this.getMemberByDiscordId(id);
        Member member = modelMapper.map(memberDto, Member.class);
        return playlistService.getPlaylistsByDiscordUser(member);
    }
}
