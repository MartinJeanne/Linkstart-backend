package com.linkstart.backend.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.OffsetDateTime;
import java.util.Date;

@Getter
@Setter
public class PurchaseDto extends RepresentationModel<PurchaseDto> {
    private Long id;
    private MemberDto member;
    private Long price;
    private OffsetDateTime created_at;
    private OffsetDateTime deliver_at;
}
