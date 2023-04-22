package com.linkstart.api.service;

import com.linkstart.api.exception.NoColumnsException;
import com.linkstart.api.exception.NoContentException;
import com.linkstart.api.exception.NoFilterGivenException;
import com.linkstart.api.exception.NotFoundException;
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

    public List<PlaylistDto> getPlaylists() {
        List<Playlist> playlists = playlistRepo.findAll();

        // TODO optimize?
        return playlists.stream().map(playlist -> {
            PlaylistDto p = modelMapper.map(playlist, PlaylistDto.class);
            p.setDiscordUserDto(modelMapper.map(playlist.getDiscordUser(), DiscordUserDto.class));
            return p;
        }).toList();
    }

    public PlaylistDto getPlaylistById(Long id) {
        Playlist playlist = playlistRepo.findById(id).orElseThrow(NoContentException::new);
        PlaylistDto PlaylistDto = modelMapper.map(playlist, PlaylistDto.class);
        return PlaylistDto;
    }

    public PlaylistDto createPlaylist(PlaylistDto playlistDto, Long discordUserId) {
        DiscordUser discordUser = discordUserRepo.findById(discordUserId)
                .orElseThrow(() -> new NotFoundException("user " + discordUserId));
        playlistDto.setDiscordUserDto(modelMapper.map(discordUser, DiscordUserDto.class));
        Playlist playlist = modelMapper.map(playlistDto, Playlist.class);
        playlistRepo.save(playlist);
        return modelMapper.map(playlist, PlaylistDto.class);
    }

    public PlaylistDto updatePlaylist(Long id, PlaylistDto PlaylistDto) {
        playlistRepo.findById(id).orElseThrow(NoContentException::new);
        PlaylistDto.setId(id);
        Playlist updatedPlaylist = playlistRepo.save(modelMapper.map(PlaylistDto, Playlist.class));
        return modelMapper.map(updatedPlaylist, PlaylistDto.class);
    }

    public void deletePlaylist(Long id) {
        Playlist playlist = playlistRepo.findById(id).orElseThrow(() -> new NotFoundException("playlist " + id));
        playlistRepo.delete(playlist);
    }

    public List<PlaylistDto> getPlaylistByDiscordUser(DiscordUser discordUser) {
        List<Playlist> playlists = playlistRepo.findByDiscordUser(discordUser);
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

