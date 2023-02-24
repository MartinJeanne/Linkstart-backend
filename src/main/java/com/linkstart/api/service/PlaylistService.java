package com.linkstart.api.service;

import com.linkstart.api.exception.NoColumnsException;
import com.linkstart.api.exception.NoContentException;
import com.linkstart.api.exception.NoFilterGivenException;
import com.linkstart.api.model.dto.DiscordUserDto;
import com.linkstart.api.model.dto.PlaylistDto;
import com.linkstart.api.model.entity.DiscordUser;
import com.linkstart.api.model.entity.Playlist;
import com.linkstart.api.repo.DiscordUserRepo;
import com.linkstart.api.repo.PlaylistRepo;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistRepo playlistRepo;
    private final DiscordUserRepo discordUserRepo;
    private final ModelMapper modelMapper;

    public PlaylistService(PlaylistRepo playlistRepo, DiscordUserRepo discordUserRepo, ModelMapper modelMapper) {
        this.playlistRepo = playlistRepo;
        this.discordUserRepo = discordUserRepo;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<List<PlaylistDto>> getPlaylists() {
        List<Playlist> playlists = playlistRepo.findAll();

        List<PlaylistDto> response = playlists
                .stream()
                .map(playlist -> modelMapper.map(playlist, PlaylistDto.class))
                .toList();
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<PlaylistDto> getPlaylistById(Long id) {
        Playlist playlist = playlistRepo.findById(id).orElseThrow(NoContentException::new);
        PlaylistDto PlaylistDto = modelMapper.map(playlist, PlaylistDto.class);
        return ResponseEntity.ok(PlaylistDto);
    }

    public ResponseEntity<PlaylistDto> createPlaylist(PlaylistDto playlistDto, Long discordUserId) {
        DiscordUser discordUser = discordUserRepo.findById(discordUserId).orElseThrow(NoContentException::new);
        playlistDto.setDiscordUserDto(modelMapper.map(discordUser, DiscordUserDto.class));
        Playlist playlist = modelMapper.map(playlistDto, Playlist.class);
        playlistRepo.save(playlist);
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(playlist, PlaylistDto.class));
    }

    public ResponseEntity<PlaylistDto> updatePlaylist(Long id, PlaylistDto PlaylistDto) {
        playlistRepo.findById(id).orElseThrow(NoContentException::new);
        PlaylistDto.setId(id);
        Playlist updatedPlaylist = playlistRepo.save(modelMapper.map(PlaylistDto, Playlist.class));
        return ResponseEntity.ok(modelMapper.map(updatedPlaylist, PlaylistDto.class));
    }

    public ResponseEntity<HttpStatus> deletePlaylist(Long id) {
        Playlist playlist = playlistRepo.findById(id).orElseThrow(NoContentException::new);
        playlistRepo.delete(playlist);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public List<PlaylistDto> getPlaylistByDiscordUser(DiscordUser discordUser) {
        List<Playlist> playlists = playlistRepo.findByDiscordUser(discordUser);
        return playlists
                .stream()
                .map(playlist -> modelMapper.map(playlist, PlaylistDto.class))
                .toList();
    }

    public ResponseEntity<List<PlaylistDto>> searchPlaylists(String filter, Integer page, Integer size, String orderBy,
            Boolean ascending) {
        if (filter.isEmpty())
            throw new NoFilterGivenException();

        List<String> columns = new ArrayList<>();
        Field[] fields = Playlist.class.getDeclaredFields();
        for (Field field : fields)
            columns.add(field.getName());
        if (!columns.contains(orderBy))
            throw new NoColumnsException(orderBy);

        Pageable pageable;
        if (ascending)
            pageable = PageRequest.of(page, size, Sort.by(orderBy));
        else
            pageable = PageRequest.of(page, size, Sort.by(orderBy).descending());
        Page<Playlist> playlists = playlistRepo.findByNameContaining(filter, pageable);

        return ResponseEntity.ok(playlists
                .stream()
                .map(playlist -> modelMapper.map(playlist, PlaylistDto.class))
                .toList());
    }
}

