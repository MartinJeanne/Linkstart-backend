package com.linkstart.backend.controller;

import com.linkstart.backend.model.dto.MemberDto;
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
    public
    ResponseEntity<CollectionModel<MemberDto>> getAllMembers(@RequestParam(required = false) String username) {
        return memberService.getAllMembers(username);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDto> getMemberById(@PathVariable("id") long id) {
        return memberService.getMemberById(id);
    }

    @PostMapping
    public ResponseEntity<MemberDto> createMember(@RequestBody MemberDto memberDto) {
        return memberService.createMember(memberDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberDto> updateMember(@PathVariable("id") long id, @RequestBody MemberDto memberDto) {
        return memberService.updateMember(id, memberDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMember(@PathVariable("id") long id) {
        return memberService.deleteMember(id);
    }
}
