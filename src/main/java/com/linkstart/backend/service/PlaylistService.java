package com.linkstart.backend.service;

import com.linkstart.backend.exception.NoColumnsException;
import com.linkstart.backend.exception.NoContentException;
import com.linkstart.backend.exception.NoContentFoundException;
import com.linkstart.backend.exception.NoFilterGivenException;
import com.linkstart.backend.mapper.MemberModelAssembler;
import com.linkstart.backend.mapper.PlaylistModelAssembler;
import com.linkstart.backend.model.dto.PlaylistDto;
import com.linkstart.backend.model.entity.Member;
import com.linkstart.backend.model.entity.Playlist;
import com.linkstart.backend.repo.MemberRepo;
import com.linkstart.backend.repo.PlaylistRepo;
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
    private final MemberRepo memberRepo;
    private final MemberModelAssembler memberModelAssembler;

    public PlaylistService(
            PlaylistRepo playlistRepo,
            PlaylistModelAssembler playlistModelAssembler,
            PagedResourcesAssembler<Playlist> pagedResourcesAssembler,
            MemberRepo memberRepo,
            MemberModelAssembler memberModelAssembler) {
        this.playlistRepo = playlistRepo;
        this.playlistModelAssembler = playlistModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.memberRepo = memberRepo;
        this.memberModelAssembler = memberModelAssembler;
    }

    public ResponseEntity<CollectionModel<PlaylistDto>> getAllPlaylists() {
        List<Playlist> playlists = playlistRepo.findAll();

        if (playlists.isEmpty()) throw new NoContentException("playlist");

        CollectionModel<PlaylistDto> response = playlistModelAssembler.toCollectionModel(playlists);
        return ResponseEntity.ok(response);
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

        if (playlists.isEmpty()) throw new NoContentException("playlist");

        PagedModel<PlaylistDto> response = pagedResourcesAssembler.toModel(playlists, playlistModelAssembler);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<PlaylistDto> getPlaylistById(Long id) {
        Playlist playlist = playlistRepo.findById(id).orElseThrow(() -> new NoContentFoundException("playlist", id));
        PlaylistDto PlaylistDto = playlistModelAssembler.toModel(playlist);
        return ResponseEntity.ok(PlaylistDto);
    }

    public ResponseEntity<PlaylistDto> createPlaylist(PlaylistDto playlistDto, Long memberId) {
        Member member = this.memberRepo.findById(memberId).orElseThrow(() -> new NoContentFoundException("user", memberId));
        playlistDto.setMember(memberModelAssembler.toModel(member));
        Playlist playlist = playlistModelAssembler.toEntity(playlistDto);
        playlistRepo.save(playlist);
        return ResponseEntity.status(HttpStatus.CREATED).body(playlistModelAssembler.toModel(playlist));
    }

    public ResponseEntity<PlaylistDto> updatePlaylist(Long id, PlaylistDto PlaylistDto) {
        playlistRepo.findById(id).orElseThrow(() -> new NoContentFoundException("playlist", id));
        PlaylistDto.setId(id);
        Playlist updatedPlaylist = playlistRepo.save(playlistModelAssembler.toEntity(PlaylistDto));
        return ResponseEntity.ok(playlistModelAssembler.toModel(updatedPlaylist));
    }

    public ResponseEntity<HttpStatus> deletePlaylist(Long id) {
        Playlist playlist = playlistRepo.findById(id).orElseThrow(() -> new NoContentFoundException("user", id));
        playlistRepo.delete(playlist);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity<CollectionModel<PlaylistDto>> getPlaylistsByMemberId(Long id) {
        List<Playlist> playlists = playlistRepo.findByMember_Id(id);

        CollectionModel<PlaylistDto> response = playlistModelAssembler.toCollectionModel(playlists);
        return ResponseEntity.ok(response);
    }
}

