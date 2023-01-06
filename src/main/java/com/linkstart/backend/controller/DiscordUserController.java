package com.linkstart.backend.controller;

import com.linkstart.backend.model.dto.DiscordUserDto;
import com.linkstart.backend.model.dto.PlaylistDto;
import com.linkstart.backend.service.DiscordUserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/discordUsers")
public class DiscordUserController {

    private final DiscordUserService discordUserService;

    public DiscordUserController(DiscordUserService discordUserService) {
        this.discordUserService = discordUserService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<DiscordUserDto>> getDiscordUsers() {
        CollectionModel<DiscordUserDto> discordUsersDto = discordUserService.getDiscordUsers();
        return ResponseEntity.ok(discordUsersDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscordUserDto> getDiscordUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(discordUserService.getDiscordUserById(id));
    }

    @PostMapping
    public ResponseEntity<DiscordUserDto> createDiscordUser(@RequestBody DiscordUserDto discordUserDto) {
        DiscordUserDto discordUserDtoCreated = discordUserService.createDiscordUser(discordUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(discordUserDtoCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiscordUserDto> updateDiscordUser(@PathVariable("id") Long id, @RequestBody DiscordUserDto discordUserDto) {
        DiscordUserDto discordUserDtoUpdated = discordUserService.updateDiscordUser(id, discordUserDto);
        return ResponseEntity.ok(discordUserDtoUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteDiscordUser(@PathVariable("id") Long id) {
        return discordUserService.deleteDiscordUser(id);
    }

    @GetMapping("/{id}/playlists")
    public ResponseEntity<CollectionModel<PlaylistDto>> getDiscordUserByIdPlaylists(@PathVariable("id") Long id) {
        CollectionModel<PlaylistDto> playlistsDto = discordUserService.getDiscordUserByIdPlaylists(id);
        return ResponseEntity.ok(playlistsDto);
    }
}
