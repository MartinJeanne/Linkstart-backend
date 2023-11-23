package com.linkstart.api.controller;

import com.linkstart.api.model.dto.DiscordUserDto;
import com.linkstart.api.model.dto.PlaylistDto;
import com.linkstart.api.service.DiscordUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discordUsers")
public class DiscordUserController {

    private final DiscordUserService discordUserService;

    public DiscordUserController(DiscordUserService discordUserService) {
        this.discordUserService = discordUserService;
    }

    @GetMapping
    public ResponseEntity<List<DiscordUserDto>> getDiscordUsers() {
        List<DiscordUserDto> discordUsersDto = discordUserService.getDiscordUsers();
        return ResponseEntity.ok(discordUsersDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscordUserDto> getDiscordUserByDiscordId(@PathVariable("id") String id) {
        return ResponseEntity.ok(discordUserService.getDiscordUserByDiscordId(id));
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
    public ResponseEntity<List<PlaylistDto>> getPlaylistsByDiscordUser(@PathVariable("id") String id) {
        List<PlaylistDto> playlistsDto = discordUserService.getPlaylistsByDiscordUser(id);
        return ResponseEntity.ok(playlistsDto);
    }

    @GetMapping("/birthday")
    public ResponseEntity<List<DiscordUserDto>> checkBirthday() {
        List<DiscordUserDto> discordUsersDto = discordUserService.getDiscordUsersBirthdayIsNow();
        return ResponseEntity.ok(discordUsersDto);
    }
}
