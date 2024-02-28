package com.linkstart.api.controller;

import com.linkstart.api.model.dto.DiscordServerDto;
import com.linkstart.api.model.dto.DiscordUserDto;
import com.linkstart.api.model.dto.PlaylistDto;
import com.linkstart.api.service.DiscordServerService;
import com.linkstart.api.service.PlaylistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discordServers")
public class DiscordServerController {

    private final DiscordServerService discordServerService;

    public DiscordServerController(DiscordServerService discordServerService) {
        this.discordServerService = discordServerService;
    }

    @GetMapping
    public ResponseEntity<List<DiscordServerDto>> getDiscordServers() {
        return ResponseEntity.ok(discordServerService.getDiscordServers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscordServerDto> getDiscordServerById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(discordServerService.getDiscordServerById(id));
    }

    @PostMapping
    public ResponseEntity<DiscordServerDto> createDiscordServer(@RequestBody DiscordServerDto discordServerDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(discordServerService.createDiscordServer(discordServerDto));
    }
}
