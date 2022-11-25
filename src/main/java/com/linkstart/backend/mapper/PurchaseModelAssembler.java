package com.linkstart.backend.mapper;

import com.linkstart.backend.controller.MemberController;
import com.linkstart.backend.controller.PurchaseController;
import com.linkstart.backend.model.dto.MemberDto;
import com.linkstart.backend.model.dto.PurchaseDto;
import com.linkstart.backend.model.entity.Member;
import com.linkstart.backend.model.entity.Purchase;
import com.linkstart.backend.repo.MemberRepo;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static java.time.OffsetDateTime.now;
import static java.time.ZoneOffset.UTC;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PurchaseModelAssembler extends RepresentationModelAssemblerSupport<Purchase, PurchaseDto> {

    private final MemberModelAssembler memberModelAssembler;

    public PurchaseModelAssembler(MemberModelAssembler memberModelAssembler) {
        super(PurchaseController.class, PurchaseDto.class);
        this.memberModelAssembler = memberModelAssembler;
    }

    // to model (Dto)
    @Override
    public PurchaseDto toModel(Purchase purchase) {
        PurchaseDto purchaseDto = new PurchaseDto();
        purchaseDto.setId(purchase.getId());
        purchaseDto.setMember(memberModelAssembler.toModel(purchase.getMember()));
        purchaseDto.setPrice(purchase.getPrice());
        purchaseDto.setCreated_at(purchase.getCreated_at());
        purchaseDto.setDeliver_at(purchase.getDeliver_at());

        purchaseDto.add(linkTo(methodOn(PurchaseController.class).getPurchaseById(purchaseDto.getId())).withSelfRel());
        purchaseDto.add(linkTo(PurchaseController.class).withRel("purchases"));

        return purchaseDto;
    }

    //to model (Dto) as list
    @Override
    public CollectionModel<PurchaseDto> toCollectionModel(Iterable<? extends Purchase> purchases) {
        List<PurchaseDto> purchasesList = new ArrayList<>();

        for (Purchase purchase: purchases) {
            PurchaseDto purchaseDto = new PurchaseDto();
            purchaseDto.setPrice(purchase.getPrice());
            purchaseDto.setCreated_at(purchase.getCreated_at());
            purchaseDto.setDeliver_at(purchase.getDeliver_at());

            purchaseDto.add(linkTo(methodOn(PurchaseController.class)
                    .getPurchaseById(purchaseDto.getId())).withSelfRel());
            purchasesList.add(purchaseDto);
        }

        CollectionModel<PurchaseDto> purchasesDto = CollectionModel.of(purchasesList);
        purchasesDto.add(linkTo(PurchaseController.class).withSelfRel());
        return purchasesDto;
    }

    // to entity
    public Purchase toEntity(PurchaseDto purchaseDto) {
        if (purchaseDto == null) return null;

        Purchase purchase = new Purchase();
        purchase.setId(purchase.getId());
        purchase.setMember(memberModelAssembler.toEntity(purchaseDto.getMember()));
        purchase.setPrice(purchaseDto.getPrice());
        purchase.setCreated_at(now(UTC));
        purchase.setDeliver_at(OffsetDateTime.of(
                LocalDate.of(2022, 12, 1),
                LocalTime.of(12, 15, 30),
                ZoneOffset.of("+03:00"))
        );
        return purchase;
    }
}
