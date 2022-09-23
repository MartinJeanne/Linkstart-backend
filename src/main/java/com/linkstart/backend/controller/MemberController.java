package com.linkstart.backend.controller;

import com.linkstart.backend.model.Member;
import com.linkstart.backend.repo.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MemberController {

    @Autowired
    MemberRepo memberRepo;

    @GetMapping("/members")
    public ResponseEntity<List<Member>> getAllMembers(@RequestParam(required = false) String username) {
        try {
            List<Member> members = new ArrayList<>();

            if (username == null) members.addAll(memberRepo.findAll());
            else members.addAll(memberRepo.findByUsernameContaining(username));

            if (members.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(members, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable("id") long id) {
        Optional<Member> member = memberRepo.findById(id);

        if (member.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else return new ResponseEntity<>(member.get(), HttpStatus.OK);
    }

    @PostMapping("/members")
    public ResponseEntity<Member> createTutorial(@RequestBody Member member) {
        try {
            Member m = memberRepo.save(new Member(member.getUsername(), member.getDiscordId()));
            return new ResponseEntity<>(m, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/members/{id}")
    public ResponseEntity<Member> updateTutorial(@PathVariable("id") long id, @RequestBody Member member) {
        Optional<Member> optionalMember = memberRepo.findById(id);

        if (optionalMember.isPresent()) {
            Member m = optionalMember.get();
            m.setUsername(member.getUsername());
            m.setDiscordId(member.getDiscordId());
            return new ResponseEntity<>(memberRepo.save(m), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
        try {
            memberRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
