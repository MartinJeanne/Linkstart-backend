package com.linkstart.api.service;


import com.linkstart.api.exception.NoContentException;
import com.linkstart.api.mapper.DiscordUserModelAssembler;
import com.linkstart.api.model.dto.PlaylistDto;
import com.linkstart.api.model.entity.DiscordUser;
import com.linkstart.api.model.dto.DiscordUserDto;
import com.linkstart.api.repo.DiscordUserRepo;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscordUserService {

    private final DiscordUserRepo discordUserRepo;
    private final DiscordUserModelAssembler discordUserModelAssembler;
    private final PlaylistService playlistService;

    public DiscordUserService(
            DiscordUserRepo discordUserRepo,
            DiscordUserModelAssembler discordUserModelAssembler,
            PagedResourcesAssembler<DiscordUser> memberPagedResourcesAssembler,
            PlaylistService playlistService) {
        this.discordUserRepo = discordUserRepo;
        this.discordUserModelAssembler = discordUserModelAssembler;
        this.playlistService = playlistService;
    }

    public CollectionModel<DiscordUserDto> getDiscordUsers() {
        List<DiscordUser> discordUsers = discordUserRepo.findAll();
        return discordUserModelAssembler.toCollectionModel(discordUsers);
    }

    public DiscordUserDto getDiscordUserById(Long id) {
        DiscordUser discordUser = discordUserRepo.findById(id).orElseThrow(NoContentException::new);
        return discordUserModelAssembler.toModel(discordUser);
    }

    public DiscordUserDto createDiscordUser(DiscordUserDto discordUserDto) {
        DiscordUser discordUser = discordUserModelAssembler.toEntity(discordUserDto);
        discordUserRepo.save(discordUser);
        return discordUserModelAssembler.toModel(discordUser);
    }

    public DiscordUserDto updateDiscordUser(Long id, DiscordUserDto discordUserDto) {
        discordUserRepo.findById(id).orElseThrow(NoContentException::new);
        discordUserDto.setId(id);
        DiscordUser updatedDiscordUser = discordUserRepo.save(discordUserModelAssembler.toEntity(discordUserDto));
        return discordUserModelAssembler.toModel(updatedDiscordUser);
    }

    public ResponseEntity<HttpStatus> deleteDiscordUser(Long id) {
        DiscordUser discordUser = discordUserRepo.findById(id).orElseThrow(NoContentException::new);
        discordUserRepo.delete(discordUser);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public CollectionModel<PlaylistDto> getDiscordUserByIdPlaylists(Long id) {
        DiscordUserDto discordUserDto = this.getDiscordUserById(id);
        DiscordUser discordUser = discordUserModelAssembler.toEntity(discordUserDto);

        CollectionModel<PlaylistDto> playlists = playlistService.getPlaylistByDiscordUser(discordUser);
        return  playlists;
    }
}
