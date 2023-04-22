package com.linkstart.api.controller;

import com.linkstart.api.model.dto.DiscordMessageDto;
import com.linkstart.api.model.dto.RoleReactionDto;
import com.linkstart.api.service.DiscordMessageService;
import com.linkstart.api.service.RoleReactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roleReactions")
public class RoleReactionController {

    private final RoleReactionService roleReactionService;

    public RoleReactionController(RoleReactionService roleReactionService) {
        this.roleReactionService = roleReactionService;
    }

    @GetMapping
    public ResponseEntity<RoleReactionDto> getRoleReactions(@RequestParam String discordId, @RequestParam String reaction) {
        return ResponseEntity.ok(roleReactionService.getRoleReaction(discordId, reaction));
    }
}
