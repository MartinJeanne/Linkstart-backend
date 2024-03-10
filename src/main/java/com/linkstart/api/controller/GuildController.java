package com.linkstart.api.controller;

import com.linkstart.api.model.dto.GuildDto;
import com.linkstart.api.service.GuildService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guilds")
public class GuildController {

    private final GuildService guildService;

    public GuildController(GuildService guildService) {
        this.guildService = guildService;
    }

    @GetMapping
    public ResponseEntity<List<GuildDto>> getGuilds() {
        return ResponseEntity.ok(guildService.getGuilds());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuildDto> getGuildById(@PathVariable("id") String id) {
        return ResponseEntity.ok(guildService.getGuildById(id));
    }

    @PostMapping
    public ResponseEntity<GuildDto> createGuild(@RequestBody GuildDto guildDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(guildService.createGuild(guildDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GuildDto> updateGuild(@PathVariable("id") String id, @RequestBody GuildDto guildDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(guildService.updateGuild(id, guildDto));
    }
}
