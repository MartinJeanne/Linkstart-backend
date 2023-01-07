package com.linkstart.api.service;

import com.linkstart.api.exception.NoColumnsException;
import com.linkstart.api.exception.NoContentException;
import com.linkstart.api.exception.NoFilterGivenException;
import com.linkstart.api.mapper.DiscordUserModelAssembler;
import com.linkstart.api.mapper.PlaylistModelAssembler;
import com.linkstart.api.model.dto.PlaylistDto;
import com.linkstart.api.model.entity.DiscordUser;
import com.linkstart.api.model.entity.Playlist;
import com.linkstart.api.repo.DiscordUserRepo;
import com.linkstart.api.repo.PlaylistRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistRepo playlistRepo;
    private final PlaylistModelAssembler playlistModelAssembler;
    private final PagedResourcesAssembler<Playlist> pagedResourcesAssembler;
    private final DiscordUserRepo discordUserRepo;
    private final DiscordUserModelAssembler discordUserModelAssembler;

    public PlaylistService(
            PlaylistRepo playlistRepo,
            PlaylistModelAssembler playlistModelAssembler,
            PagedResourcesAssembler<Playlist> pagedResourcesAssembler,
            DiscordUserRepo discordUserRepo,
            DiscordUserModelAssembler discordUserModelAssembler) {
        this.playlistRepo = playlistRepo;
        this.playlistModelAssembler = playlistModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.discordUserRepo = discordUserRepo;
        this.discordUserModelAssembler = discordUserModelAssembler;
    }

    public ResponseEntity<CollectionModel<PlaylistDto>> getPlaylists() {
        List<Playlist> playlists = playlistRepo.findAll();

        CollectionModel<PlaylistDto> response = playlistModelAssembler.toCollectionModel(playlists);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<PlaylistDto> getPlaylistById(Long id) {
        Playlist playlist = playlistRepo.findById(id).orElseThrow(NoContentException::new);
        PlaylistDto PlaylistDto = playlistModelAssembler.toModel(playlist);
        return ResponseEntity.ok(PlaylistDto);
    }

    public ResponseEntity<PlaylistDto> createPlaylist(PlaylistDto playlistDto, Long discordUserId) {
        DiscordUser discordUser = discordUserRepo.findById(discordUserId).orElseThrow(NoContentException::new);
        playlistDto.setDiscordUserDto(discordUserModelAssembler.toModel(discordUser));
        Playlist playlist = playlistModelAssembler.toEntity(playlistDto);
        playlistRepo.save(playlist);
        return ResponseEntity.status(HttpStatus.CREATED).body(playlistModelAssembler.toModel(playlist));
    }

    public ResponseEntity<PlaylistDto> updatePlaylist(Long id, PlaylistDto PlaylistDto) {
        playlistRepo.findById(id).orElseThrow(NoContentException::new);
        PlaylistDto.setId(id);
        Playlist updatedPlaylist = playlistRepo.save(playlistModelAssembler.toEntity(PlaylistDto));
        return ResponseEntity.ok(playlistModelAssembler.toModel(updatedPlaylist));
    }

    public ResponseEntity<HttpStatus> deletePlaylist(Long id) {
        Playlist playlist = playlistRepo.findById(id).orElseThrow(NoContentException::new);
        playlistRepo.delete(playlist);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public CollectionModel<PlaylistDto> getPlaylistByDiscordUser(DiscordUser discordUser) {
        List<Playlist> playlists = playlistRepo.findByDiscordUser(discordUser);
        return playlistModelAssembler.toCollectionModel(playlists);
    }

    public ResponseEntity<CollectionModel<PlaylistDto>> searchPlaylists(
            String filter, Integer page, Integer size, String orderBy, Boolean ascending) {
        if (filter.isEmpty()) throw new NoFilterGivenException();

        List<String> columns = new ArrayList<>();
        Field[] fields = Playlist.class.getDeclaredFields();
        for (Field field : fields) columns.add(field.getName());
        if (!columns.contains(orderBy)) throw new NoColumnsException(orderBy);

        Pageable pageable;
        if (ascending) pageable = PageRequest.of(page, size, Sort.by(orderBy));
        else pageable = PageRequest.of(page, size, Sort.by(orderBy).descending());
        Page<Playlist> playlists = playlistRepo.findByNameContaining(filter, pageable);

        PagedModel<PlaylistDto> response = pagedResourcesAssembler.toModel(playlists, playlistModelAssembler);
        return ResponseEntity.ok(response);
    }
}

