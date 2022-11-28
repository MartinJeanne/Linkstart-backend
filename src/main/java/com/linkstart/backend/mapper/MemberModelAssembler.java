package com.linkstart.backend.mapper;

import com.linkstart.backend.controller.MemberController;
import com.linkstart.backend.controller.PurchaseController;
import com.linkstart.backend.model.entity.Member;
import com.linkstart.backend.model.dto.MemberDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MemberModelAssembler extends RepresentationModelAssemblerSupport<Member, MemberDto> {

    public MemberModelAssembler() {
        super(MemberController.class, MemberDto.class);
    }

    // to model (Dto)
    @Override
    public MemberDto toModel(Member member) {
        MemberDto memberDto = new MemberDto();
        memberDto.setId(member.getId());
        memberDto.setUsername(member.getUsername());
        memberDto.setMail(member.getMail());

        memberDto.add(linkTo(methodOn(PurchaseController.class).getPurchaseByMemberId(memberDto.getId()))
                .withRel("member purchases"));
        memberDto.add(linkTo(MemberController.class).withRel("members"));
        memberDto.add(linkTo(methodOn(MemberController.class).getMemberById(memberDto.getId())).withSelfRel());

        return memberDto;
    }

    //to model (Dto) as list
    @Override
    public CollectionModel<MemberDto> toCollectionModel(Iterable<? extends Member> members) {
        List<MemberDto> membersList = new ArrayList<>();

        for (Member member: members) {
            MemberDto memberDto = new MemberDto();
            memberDto.setId(member.getId());
            memberDto.setUsername(member.getUsername());
            memberDto.setMail(member.getMail());

            memberDto.add(linkTo(methodOn(PurchaseController.class).getPurchaseByMemberId(memberDto.getId()))
                    .withRel("member purchases"));
            memberDto.add(linkTo(methodOn(MemberController.class).getMemberById(memberDto.getId())).withSelfRel());
            membersList.add(memberDto);
        }

        CollectionModel<MemberDto> membersDto = CollectionModel.of(membersList);
        membersDto.add(linkTo(MemberController.class).withSelfRel());
        return membersDto;
    }

    // to entity
    public Member toEntity(MemberDto memberDto) {
        if (memberDto == null) return null;

        Member member = new Member();
        member.setId(memberDto.getId());
        member.setUsername(memberDto.getUsername());
        member.setMail(memberDto.getMail());
        return  member;
    }
}
