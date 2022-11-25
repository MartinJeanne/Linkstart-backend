package com.linkstart.backend.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class MemberDto extends RepresentationModel<MemberDto> {
    private Long id;
    private String username;
    private String mail;
}
