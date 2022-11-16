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

            if (members.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

            CollectionModel<MemberDto> response = memberModelAssembler.toCollectionModel(members);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<MemberDto> getMemberById(long id) {
        Optional<Member> optMember = memberRepo.findById(id);

        if (optMember.isPresent()) {
            MemberDto memberDto = memberModelAssembler.toModel(optMember.get());
            return ResponseEntity.ok(memberDto);
        }
        else return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity<MemberDto> createMember(MemberDto memberDto) {
        try {
            Member member = memberModelAssembler.toEntity(memberDto);
            memberRepo.save(member);
            return ResponseEntity.status(HttpStatus.CREATED).body(memberModelAssembler.toModel(member));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<MemberDto> updateMember(long id, MemberDto memberDto) {
        Optional<Member> optMember = memberRepo.findById(id);
        if (optMember.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        memberDto.setId(id);
        Member member = memberModelAssembler.toEntity(memberDto);
        Member updatedMember = memberRepo.save(member);

        return ResponseEntity.ok(memberModelAssembler.toModel(updatedMember));
    }

    public ResponseEntity<HttpStatus> deleteMember(long id) {
        try {
            memberRepo.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
