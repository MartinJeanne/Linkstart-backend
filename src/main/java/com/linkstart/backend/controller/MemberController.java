package com.linkstart.backend.controller;

import com.linkstart.backend.model.dto.MemberDto;
import com.linkstart.backend.model.dto.PlaylistDto;
import com.linkstart.backend.service.MemberService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<MemberDto>> getAllMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/search")
    public ResponseEntity<CollectionModel<MemberDto>> searchMembers(
            @RequestParam String filter,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "2") Integer size,
            @RequestParam(defaultValue = "username") String orderBy,
            @RequestParam(defaultValue = "true") Boolean ascending) {
        return memberService.searchMembers(filter, page, size, orderBy, ascending);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDto> getMemberById(@PathVariable("id") Long id) {
        return memberService.getMemberById(id);
    }

    @PostMapping
    public ResponseEntity<MemberDto> createMember(@RequestBody MemberDto memberDto) {
        return memberService.createMember(memberDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberDto> updateMember(@PathVariable("id") Long id, @RequestBody MemberDto memberDto) {
        return memberService.updateMember(id, memberDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMember(@PathVariable("id") Long id) {
        return memberService.deleteMember(id);
    }

    @GetMapping("/{id}/playlists")
    public ResponseEntity<CollectionModel<PlaylistDto>> getPlaylistsByMemberId(@PathVariable("id") Long id) {
        return memberService.getPlaylistsByMemberId(id);
    }
}
