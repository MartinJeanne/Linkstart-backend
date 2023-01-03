package com.linkstart.backend.service;

import com.linkstart.backend.exception.NoColumnsException;
import com.linkstart.backend.exception.NoFilterGivenException;
import com.linkstart.backend.exception.NoContentException;
import com.linkstart.backend.exception.NoContentFoundException;
import com.linkstart.backend.mapper.MemberModelAssembler;
import com.linkstart.backend.model.entity.Member;
import com.linkstart.backend.model.dto.MemberDto;
import com.linkstart.backend.repo.MemberRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {

    private final MemberRepo memberRepo;
    private final MemberModelAssembler memberModelAssembler;
    private final PagedResourcesAssembler<Member> pagedResourcesAssembler;

    public MemberService(
            MemberRepo memberRepo,
            MemberModelAssembler memberModelAssembler,
            PagedResourcesAssembler<Member> pagedResourcesAssembler) {
        this.memberRepo = memberRepo;
        this.memberModelAssembler = memberModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    public ResponseEntity<CollectionModel<MemberDto>> getAllMembers() {
        List<Member> members = memberRepo.findAll();

        if (members.isEmpty()) throw new NoContentException("user");

        CollectionModel<MemberDto> response = memberModelAssembler.toCollectionModel(members);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<CollectionModel<MemberDto>> searchMembers(
            String filter, Integer page, Integer size, String orderBy, Boolean ascending) {
        if (filter.isEmpty()) throw new NoFilterGivenException();

        List<String> columns = new ArrayList<>();
        Field[] fields = Member.class.getDeclaredFields();
        for(Field field: fields) columns.add(field.getName());
        if (!columns.contains(orderBy)) throw new NoColumnsException(orderBy);

        Pageable pageable;
        if (ascending) pageable = PageRequest.of(page, size, Sort.by(orderBy));
        else pageable = PageRequest.of(page, size, Sort.by(orderBy).descending());
        Page<Member> members = memberRepo.findByTagContaining(filter, pageable);

        if (members.isEmpty()) throw new NoContentException("member");

        PagedModel<MemberDto> response = pagedResourcesAssembler.toModel(members, memberModelAssembler);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<MemberDto> getMemberById(Long id) {
        Member member = memberRepo.findById(id).orElseThrow(() -> new NoContentFoundException("user", id));
        MemberDto memberDto = memberModelAssembler.toModel(member);
        return ResponseEntity.ok(memberDto);
    }

    public ResponseEntity<MemberDto> createMember(MemberDto memberDto) {
        Member member = memberModelAssembler.toEntity(memberDto);
        memberRepo.save(member);
        return ResponseEntity.status(HttpStatus.CREATED).body(memberModelAssembler.toModel(member));
    }

    public ResponseEntity<MemberDto> updateMember(Long id, MemberDto memberDto) {
        memberRepo.findById(id).orElseThrow(() -> new NoContentFoundException("user", id));
        memberDto.setId(id);
        Member updatedMember = memberRepo.save(memberModelAssembler.toEntity(memberDto));

        return ResponseEntity.ok(memberModelAssembler.toModel(updatedMember));
    }

    public ResponseEntity<HttpStatus> deleteMember(Long id) {
        Member member = memberRepo.findById(id).orElseThrow(() -> new NoContentFoundException("user", id));
        memberRepo.delete(member);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
