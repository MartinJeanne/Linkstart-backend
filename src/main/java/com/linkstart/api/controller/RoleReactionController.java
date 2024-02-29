package com.linkstart.api.controller;

import com.linkstart.api.model.dto.RoleReactionDto;
import com.linkstart.api.service.RoleReactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roleReactions")
public class RoleReactionController {

    private final RoleReactionService roleReactionService;

    public RoleReactionController(RoleReactionService roleReactionService) {
        this.roleReactionService = roleReactionService;
    }

    @GetMapping
    public ResponseEntity<RoleReactionDto> getRoleReactions(@RequestParam long id, @RequestParam String reaction) {
        return ResponseEntity.ok(roleReactionService.getRoleReaction(id, reaction));
    }
}
