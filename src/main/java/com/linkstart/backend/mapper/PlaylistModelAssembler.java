package com.linkstart.backend.mapper;

import com.linkstart.backend.controller.PlaylistController;
import com.linkstart.backend.model.dto.PlaylistDto;
import com.linkstart.backend.model.entity.Playlist;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PlaylistModelAssembler extends RepresentationModelAssemblerSupport<Playlist, PlaylistDto> {

    private final MemberModelAssembler memberModelAssembler;

    public PlaylistModelAssembler(MemberModelAssembler memberModelAssembler) {
        super(PlaylistController.class, PlaylistDto.class);
        this.memberModelAssembler = memberModelAssembler;
    }

    // to model (Dto)
    @Override
    public PlaylistDto toModel(Playlist playlist) {
        PlaylistDto playlistDto = new PlaylistDto();
        playlistDto.setId(playlist.getId());
        playlistDto.setMember(memberModelAssembler.toModel(playlist.getMember()));
        playlistDto.setName(playlist.getName());
        playlistDto.setCreated_at(playlist.getCreated_at());

        playlistDto.add(linkTo(methodOn(PlaylistController.class).getPlaylistById(playlist.getId())).withSelfRel());
        playlistDto.add(linkTo(PlaylistController.class).withRel("Playlists"));

        return playlistDto;
    }

    //to model (Dto) as list
    @Override
    public CollectionModel<PlaylistDto> toCollectionModel(Iterable<? extends Playlist> playlists) {
        List<PlaylistDto> playlistsList = new ArrayList<>();

        for (Playlist playlist: playlists) {
            PlaylistDto playlistDto = new PlaylistDto();
            playlistDto.setId(playlist.getId());
            playlistDto.setMember(memberModelAssembler.toModel(playlist.getMember()));
            playlistDto.setName(playlist.getName());
            playlistDto.setCreated_at(playlist.getCreated_at());

            playlistDto.add(linkTo(methodOn(PlaylistController.class).getPlaylistById(playlistDto.getId())).withSelfRel());
            playlistsList.add(playlistDto);
        }

        CollectionModel<PlaylistDto> PlaylistsDto = CollectionModel.of(playlistsList);
        PlaylistsDto.add(linkTo(PlaylistController.class).withSelfRel());
        return PlaylistsDto;
    }

    // to entity
    public Playlist toEntity(PlaylistDto playlisDto) {
        if (playlisDto == null) return null;

        Playlist playlist = new Playlist();
        playlist.setId(playlist.getId());
        playlist.setMember(memberModelAssembler.toEntity(playlisDto.getMember()));
        playlist.setName(playlisDto.getName());
        playlist.setCreated_at(new Date(System.currentTimeMillis()));
        return playlist;
    }
}
