package com.linkstart.backend.service;

import com.linkstart.backend.exception.NoUserException;
import com.linkstart.backend.exception.UserNotFoundException;
import com.linkstart.backend.mapper.MemberModelAssembler;
import com.linkstart.backend.model.entity.Member;
import com.linkstart.backend.model.dto.MemberDto;
import com.linkstart.backend.repo.MemberRepo;
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

    public ResponseEntity<CollectionModel<MemberDto>> getAllMembers(String filter) {
        List<Member> members = new ArrayList<>();

        // Get all members or some if search passed
        if (filter == null) members.addAll(memberRepo.findAll());
        else members.addAll(memberRepo.findByUsernameContaining(filter));

        if (members.isEmpty()) throw new NoUserException();

        CollectionModel<MemberDto> response = memberModelAssembler.toCollectionModel(members);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<MemberDto> getMemberById(long id) throws UserNotFoundException {
        Member member = memberRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        MemberDto memberDto = memberModelAssembler.toModel(member);
        return ResponseEntity.ok(memberDto);
    }

    public ResponseEntity<MemberDto> createMember(MemberDto memberDto) {
        Member member = memberModelAssembler.toEntity(memberDto);
        memberRepo.save(member);
        return ResponseEntity.status(HttpStatus.CREATED).body(memberModelAssembler.toModel(member));
    }

    public ResponseEntity<MemberDto> updateMember(long id, MemberDto memberDto) throws UserNotFoundException {
        memberRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        memberDto.setId(id);
        Member updatedMember = memberRepo.save(memberModelAssembler.toEntity(memberDto));

        return ResponseEntity.ok(memberModelAssembler.toModel(updatedMember));
    }

    public ResponseEntity<HttpStatus> deleteMember(long id) throws UserNotFoundException {
        Member member = memberRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        memberRepo.delete(member);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
