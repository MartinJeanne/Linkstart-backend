package com.linkstart.api.controller;

import com.linkstart.api.model.dto.MemberDto;
import com.linkstart.api.model.dto.PlaylistDto;
import com.linkstart.api.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<MemberDto>> getMember() {
        List<MemberDto> members = memberService.getMember();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDto> getMemberByDiscordId(@PathVariable("id") String id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    @PostMapping
    public ResponseEntity<MemberDto> createMember(@RequestBody MemberDto memberDto) {
        MemberDto memberDtoCreated = memberService.createMember(memberDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(memberDtoCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberDto> updateMember(@PathVariable("id") String id, @RequestBody MemberDto memberDto) {
        MemberDto memberDtoUpdated = memberService.updateMember(id, memberDto);
        return ResponseEntity.ok(memberDtoUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMember(@PathVariable("id") String id) {
        return memberService.deleteMember(id);
    }

    @GetMapping("/{id}/playlists")
    public ResponseEntity<List<PlaylistDto>> getPlaylistsByMember(@PathVariable("id") String id) {
        List<PlaylistDto> playlistsDto = memberService.getPlaylistsByMember(id);
        return ResponseEntity.ok(playlistsDto);
    }

    @GetMapping("/birthdayIsToday")
    public ResponseEntity<List<MemberDto>> birthdayIsToday() {
        List<MemberDto> members = memberService.birthdayIsToday();
        return ResponseEntity.ok(members);
    }
}
