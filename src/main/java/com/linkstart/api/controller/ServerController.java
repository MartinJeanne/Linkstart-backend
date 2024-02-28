package com.linkstart.api.controller;

import com.linkstart.api.model.dto.ServerDto;
import com.linkstart.api.service.ServerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servers")
public class ServerController {

    private final ServerService serverService;

    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }

    @GetMapping
    public ResponseEntity<List<ServerDto>> getServers() {
        return ResponseEntity.ok(serverService.getServers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServerDto> getServerById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(serverService.getServerById(id));
    }

    @PostMapping
    public ResponseEntity<ServerDto> createServer(@RequestBody ServerDto serverDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(serverService.createServer(serverDto));
    }
}
