package com.linkstart.backend.service;

import com.linkstart.backend.exception.NoColumnsException;
import com.linkstart.backend.exception.NoFilterGivenException;
import com.linkstart.backend.exception.NoUserException;
import com.linkstart.backend.exception.UserNotFoundException;
import com.linkstart.backend.mapper.MemberModelAssembler;
import com.linkstart.backend.mapper.PurchaseModelAssembler;
import com.linkstart.backend.model.dto.MemberDto;
import com.linkstart.backend.model.dto.PurchaseDto;
import com.linkstart.backend.model.entity.Member;
import com.linkstart.backend.model.entity.Purchase;
import com.linkstart.backend.repo.MemberRepo;
import com.linkstart.backend.repo.PurchaseRepo;
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
import java.util.Optional;

@Service
public class PurchaseService {

    private final PurchaseRepo purchaseRepo;

    private final PurchaseModelAssembler purchaseModelAssembler;

    private final MemberModelAssembler memberModelAssembler;

    private final MemberRepo memberRepo;

    private final PagedResourcesAssembler<Purchase> pagedResourcesAssembler;

    public PurchaseService(
            PurchaseRepo purchaseRepo,
            PagedResourcesAssembler<Purchase> pagedResourcesAssembler,
            PurchaseModelAssembler purchaseModelAssembler,
            MemberRepo memberRepo,
            MemberModelAssembler memberModelAssembler) {
        this.purchaseRepo = purchaseRepo;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.purchaseModelAssembler = purchaseModelAssembler;
        this.memberRepo = memberRepo;
        this.memberModelAssembler = memberModelAssembler;
    }

    public ResponseEntity<CollectionModel<PurchaseDto>> getAllPurchases() {
        List<Purchase> purchases = purchaseRepo.findAll();

        if (purchases.isEmpty()) throw new NoUserException();

        CollectionModel<PurchaseDto> response = purchaseModelAssembler.toCollectionModel(purchases);
        return ResponseEntity.ok(response);
    }

    /*
    public ResponseEntity<CollectionModel<PurchaseDto>> searchPurchases(
            String filter, Integer page, Integer size, String orderBy, Boolean ascending) {
        if (filter.isEmpty()) throw new NoFilterGivenException();

        List<String> columns = new ArrayList<>();
        Field[] fields = Purchase.class.getDeclaredFields();
        for(Field field: fields) columns.add(field.getName());
        if (!columns.contains(orderBy)) throw new NoColumnsException(orderBy);

        Pageable pageable;
        if (ascending) pageable = PageRequest.of(page, size, Sort.by(orderBy));
        else pageable = PageRequest.of(page, size, Sort.by(orderBy).descending());
        Page<Member> members = purchaseRepo.findByUsernameContaining(filter, pageable);

        if (members.isEmpty()) throw new NoUserException();

        PagedModel<MemberDto> response = pagedResourcesAssembler.toModel(members, memberModelAssembler);
        return ResponseEntity.ok(response);
    }
    */

    public ResponseEntity<PurchaseDto> getPurchaseById(Long id) {
        Purchase purchase = purchaseRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        PurchaseDto purchaseDto = purchaseModelAssembler.toModel(purchase);
        return ResponseEntity.ok(purchaseDto);
    }

    public ResponseEntity<PurchaseDto> createPurchase(PurchaseDto purchaseDto, Long memberId) {
        Member member = this.memberRepo.findById(memberId).orElseThrow(() -> new UserNotFoundException(memberId));
        purchaseDto.setMember(memberModelAssembler.toModel(member));
        Purchase purchase = purchaseModelAssembler.toEntity(purchaseDto);
        purchaseRepo.save(purchase);
        return ResponseEntity.status(HttpStatus.CREATED).body(purchaseModelAssembler.toModel(purchase));
    }

    public ResponseEntity<PurchaseDto> updatePurchase(Long id, PurchaseDto purchaseDto) {
        purchaseRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        purchaseDto.setId(id);
        Purchase updatedPurchase = purchaseRepo.save(purchaseModelAssembler.toEntity(purchaseDto));

        return ResponseEntity.ok(purchaseModelAssembler.toModel(updatedPurchase));
    }

    public ResponseEntity<HttpStatus> deletePurchase(Long id) {
        Purchase purchase = purchaseRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        purchaseRepo.delete(purchase);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
