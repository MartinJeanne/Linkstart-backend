package com.linkstart.backend.service;

import com.linkstart.backend.infrastructure.HelloInterface;
import com.linkstart.backend.mapper.MemberModelAssembler;
import com.linkstart.backend.model.entity.Member;
import com.linkstart.backend.model.dto.MemberDto;
import com.linkstart.backend.repo.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepo memberRepo;
    private final MemberModelAssembler memberModelAssembler;

    public MemberService(MemberRepo memberRepo, MemberModelAssembler memberModelAssembler) {
        this.memberRepo = memberRepo;
        this.memberModelAssembler = memberModelAssembler;
    }

    public ResponseEntity<CollectionModel<MemberDto>> getAllMembers(String username) {
        try {
            List<Member> members = new ArrayList<>();

            // Get all members or some if search passed
            if (username == null) members.addAll(memberRepo.findAll());
            else members.addAll(memberRepo.findByUsernameContaining(username));

            if (members.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            CollectionModel<MemberDto> response = memberModelAssembler.toCollectionModel(members);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<MemberDto> getMemberById(long id) {
        Optional<Member> optMember = memberRepo.findById(id);

        if (optMember.isPresent()) {
            MemberDto member = memberModelAssembler.toModel(optMember.get());
            return new ResponseEntity<>(member, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<MemberDto> createMember(MemberDto memberDto) {
        try {
            Member member = memberModelAssembler.toEntity(memberDto);
            memberRepo.save(member);
            return new ResponseEntity<>(memberModelAssembler.toModel(member), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<MemberDto> updateMember(long id, MemberDto memberDto) {
        Optional<Member> optMember = memberRepo.findById(id);
        if (optMember.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        memberDto.setId(id);
        Member member = memberModelAssembler.toEntity(memberDto);
        Member updatedMember = memberRepo.save(member);

        return new ResponseEntity<>(memberModelAssembler.toModel(updatedMember), HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> deleteMember(long id) {
        try {
            memberRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
