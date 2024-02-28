package com.linkstart.api.controller;

import com.linkstart.api.model.dto.GuildDto;
import com.linkstart.api.service.GuildService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servers")
public class GuildController {

    private final GuildService guildService;

    public GuildController(GuildService guildService) {
        this.guildService = guildService;
    }

    @GetMapping
    public ResponseEntity<List<GuildDto>> getServers() {
        return ResponseEntity.ok(guildService.getServers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuildDto> getServerById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(guildService.getServerById(id));
    }

    @PostMapping
    public ResponseEntity<GuildDto> createServer(@RequestBody GuildDto guildDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(guildService.createServer(guildDto));
    }
}
