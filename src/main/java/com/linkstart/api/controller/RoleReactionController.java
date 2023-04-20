package com.linkstart.api.controller;

import com.linkstart.api.model.dto.DiscordMessageDto;
import com.linkstart.api.model.dto.RoleReactionDto;
import com.linkstart.api.service.DiscordMessageService;
import com.linkstart.api.service.RoleReactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roleReactions")
public class RoleReactionController {

    private final RoleReactionService roleReactionService;

    public RoleReactionController(RoleReactionService roleReactionService) {
        this.roleReactionService = roleReactionService;
    }

    @GetMapping
    public ResponseEntity<List<RoleReactionDto>> getRoleReactions() {
        return ResponseEntity.ok(roleReactionService.getRoleReactions());
    }
}
