package com.linkstart.api.service;

import com.linkstart.api.exception.NoColumnsException;
import com.linkstart.api.exception.NoFilterGivenException;
import com.linkstart.api.exception.NotFoundException;
import com.linkstart.api.mapper.PlaylistMapper;
import com.linkstart.api.model.dto.PlaylistDto;
import com.linkstart.api.model.entity.Member;
import com.linkstart.api.model.entity.Playlist;
import com.linkstart.api.repo.MemberRepo;
import com.linkstart.api.repo.PlaylistRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistRepo playlistRepo;
    private final MemberRepo memberRepo;
    private final PlaylistMapper playlistMapper;

    public PlaylistService(PlaylistRepo playlistRepo, MemberRepo memberRepo, PlaylistMapper playlistMapper) {
        this.playlistRepo = playlistRepo;
        this.memberRepo = memberRepo;
        this.playlistMapper = playlistMapper;
    }

    public List<PlaylistDto> getPlaylists() {
        List<Playlist> playlists = playlistRepo.findAll();
        return playlistMapper.toDtoList(playlists);
    }

    public PlaylistDto getPlaylistById(Integer id) {
        Playlist playlist = playlistRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(id.toString(), Playlist.class));
        return playlistMapper.toDto(playlist);
    }

    public PlaylistDto createPlaylist(PlaylistDto playlistDto, String memberId) {
        Member member = memberRepo.findById(memberId)
                .orElseThrow(() -> new NotFoundException(memberId, Member.class));

        Playlist playlist = playlistMapper.toEntity(playlistDto);
        playlist.setMember(member);
        playlist.setCreated_at(LocalDateTime.now());
        Playlist savedPlaylist = playlistRepo.save(playlist);

        return playlistMapper.toDto(savedPlaylist);
    }

    public PlaylistDto updatePlaylist(Integer id, PlaylistDto PlaylistDto) {
        playlistRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(id.toString(), Playlist.class));

        Playlist updatedPlaylist = playlistRepo.save(playlistMapper.toEntity(PlaylistDto));
        return playlistMapper.toDto(updatedPlaylist);
    }

    public void deletePlaylist(Integer id) {
        Playlist playlist = playlistRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(id.toString(), Playlist.class));
        playlistRepo.delete(playlist);
    }

    public List<PlaylistDto> getPlaylistsByDiscordUser(Member member) {
        List<Playlist> playlists = playlistRepo.findByMember(member);
        return playlists.stream().map(playlistMapper::toDto).toList();
    }

    public List<PlaylistDto> searchPlaylists(String filter, Integer page, Integer size, String orderBy,
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

        return playlists.stream().map(playlistMapper::toDto).toList();
    }
}
