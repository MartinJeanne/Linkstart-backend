package com.linkstart.backend.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.sql.Date;

@Getter
@Setter
public class PurchaseDto extends RepresentationModel<PurchaseDto> {
    private Long id;
    private MemberDto member;
    private Long price;
    private Date created_at;
    private Date deliver_at;
}
