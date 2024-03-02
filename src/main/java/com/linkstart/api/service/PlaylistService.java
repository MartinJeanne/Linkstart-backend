package com.linkstart.api.service;

import com.linkstart.api.exception.NoColumnsException;
import com.linkstart.api.exception.NoContentException;
import com.linkstart.api.exception.NoFilterGivenException;
import com.linkstart.api.exception.NotFoundException;
import com.linkstart.api.model.dto.PlaylistDto;
import com.linkstart.api.model.entity.Member;
import com.linkstart.api.model.entity.Playlist;
import com.linkstart.api.repo.MemberRepo;
import com.linkstart.api.repo.PlaylistRepo;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    public PlaylistService(PlaylistRepo playlistRepo, MemberRepo memberRepo, ModelMapper modelMapper) {
        this.playlistRepo = playlistRepo;
        this.memberRepo = memberRepo;
        this.modelMapper = modelMapper;
    }

    public List<PlaylistDto> getPlaylists() {
        List<Playlist> playlists = playlistRepo.findAll();

        return playlists.stream().map(playlist -> modelMapper.map(playlist, PlaylistDto.class)).toList();
    }

    public PlaylistDto getPlaylistById(Integer id) {
        Playlist playlist = playlistRepo.findById(id).orElseThrow(NoContentException::new);
        return modelMapper.map(playlist, PlaylistDto.class);
    }

    public PlaylistDto createPlaylist(PlaylistDto playlistDto, String memberId) {
        Member member = memberRepo.findById(memberId)
                .orElseThrow(() -> new NotFoundException(memberId, Member.class));

        Playlist playlist = modelMapper.map(playlistDto, Playlist.class);
        playlist.setMember(member);
        playlist.setCreated_at(LocalDateTime.now());
        playlistRepo.save(playlist);
        return modelMapper.map(playlist, PlaylistDto.class);
    }

    public PlaylistDto updatePlaylist(int id, PlaylistDto PlaylistDto) {
        playlistRepo.findById(id).orElseThrow(NoContentException::new);
        Playlist updatedPlaylist = playlistRepo.save(modelMapper.map(PlaylistDto, Playlist.class));
        return modelMapper.map(updatedPlaylist, PlaylistDto.class);
    }

    public void deletePlaylist(Integer id) {
        Playlist playlist = playlistRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(id.toString(), Playlist.class));
        playlistRepo.delete(playlist);
    }

    public List<PlaylistDto> getPlaylistsByDiscordUser(Member member) {
        List<Playlist> playlists = playlistRepo.findByMember(member);
        return playlists.stream().map(playlist -> modelMapper.map(playlist, PlaylistDto.class)).toList();
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

        return playlists.stream().map(playlist -> modelMapper.map(playlist, PlaylistDto.class)).toList();
    }
}

