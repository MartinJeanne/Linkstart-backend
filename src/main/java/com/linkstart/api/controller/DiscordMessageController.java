package com.linkstart.api.controller;

import com.linkstart.api.model.dto.DiscordMessageDto;
import com.linkstart.api.model.dto.PlaylistDto;
import com.linkstart.api.model.entity.DiscordMessage;
import com.linkstart.api.service.DiscordMessageService;
import com.linkstart.api.service.PlaylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/discordMessages")
public class DiscordMessageController {

    private final DiscordMessageService discordMessageService;

    public DiscordMessageController(DiscordMessageService discordMessageService) {
        this.discordMessageService = discordMessageService;
    }

    @GetMapping
    public ResponseEntity<List<DiscordMessageDto>> getDiscordMessages() {
        return ResponseEntity.ok(discordMessageService.getDiscordMessages());
    }
}
