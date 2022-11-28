package com.linkstart.backend.controller;

import com.linkstart.backend.model.dto.PurchaseDto;
import com.linkstart.backend.service.PurchaseService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<PurchaseDto>> getAllPurchases() {
        return purchaseService.getAllPurchases();
    }

    /* todo
    @GetMapping("/search")
    public ResponseEntity<CollectionModel<PurchaseDto>> seahPurchases(
            @RequestParam String filter,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "2") Integer size,
            @RequestParam(defaultValue = "username") String orderBy,
            @RequestParam(defaultValue = "true") Boolean ascending) {
        return purchaseService.searchPurchases(filter, page, size, orderBy, ascending);
    }
    */

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseDto> getPurchaseById(@PathVariable("id") Long id) {
        return purchaseService.getPurchaseById(id);
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<CollectionModel<PurchaseDto>> getPurchaseByMemberId(@PathVariable("id") Long id) {
        return purchaseService.getPurchaseByMemberId(id);
    }

    @PostMapping
    public ResponseEntity<PurchaseDto> createPurchase(@RequestBody PurchaseDto purchaseDto, @RequestParam Long memberId) {
        return purchaseService.createPurchase(purchaseDto, memberId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseDto> updatePurchase(@PathVariable("id") Long id, @RequestBody PurchaseDto purchaseDto) {
        return purchaseService.updatePurchase(id, purchaseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePurchase(@PathVariable("id") Long id) {
        return purchaseService.deletePurchase(id);
    }
}
