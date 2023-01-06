package com.linkstart.backend.controller;

import com.linkstart.backend.model.dto.PlaylistDto;
import com.linkstart.backend.service.PlaylistService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<PlaylistDto>> getAllMembers() {
        return playlistService.getAllPlaylists();
    }

    @GetMapping("/search")
    public ResponseEntity<CollectionModel<PlaylistDto>> searchMembers(
            @RequestParam String filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "username") String orderBy,
            @RequestParam(defaultValue = "true") Boolean ascending) {
        return playlistService.searchPlaylists(filter, page, size, orderBy, ascending);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistDto> getPlaylistById(@PathVariable("id") Long id) {
        return playlistService.getPlaylistById(id);
    }

    @PostMapping
    public ResponseEntity<PlaylistDto> createPlaylist(@RequestBody PlaylistDto playlistDto, @RequestParam Long memberId) {
        return playlistService.createPlaylist(playlistDto, memberId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaylistDto> updatePlaylist(@PathVariable("id") Long id, @RequestBody PlaylistDto playlistDto) {
        return playlistService.updatePlaylist(id, playlistDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePlaylist(@PathVariable("id") Long id) {
        return playlistService.deletePlaylist(id);
    }

    public ResponseEntity<CollectionModel<PlaylistDto>> getPlaylistsByMemberId(Long id) {
        return playlistService.getPlaylistsByMemberId(id);
    }
}
