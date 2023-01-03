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
    public PlaylistDto toModel(Playlist Playlist) {
        PlaylistDto PlaylistDto = new PlaylistDto();
        PlaylistDto.setId(Playlist.getId());
        PlaylistDto.setMember(memberModelAssembler.toModel(Playlist.getMember()));
        PlaylistDto.setCreated_at(Playlist.getCreated_at());

        PlaylistDto.add(linkTo(methodOn(PlaylistController.class).getPlaylistById(PlaylistDto.getId())).withSelfRel());
        PlaylistDto.add(linkTo(PlaylistController.class).withRel("Playlists"));

        return PlaylistDto;
    }

    //to model (Dto) as list
    @Override
    public CollectionModel<PlaylistDto> toCollectionModel(Iterable<? extends Playlist> Playlists) {
        List<PlaylistDto> PlaylistsList = new ArrayList<>();

        for (Playlist Playlist: Playlists) {
            PlaylistDto PlaylistDto = new PlaylistDto();
            PlaylistDto.setId(Playlist.getId());
            PlaylistDto.setMember(memberModelAssembler.toModel(Playlist.getMember()));
            PlaylistDto.setCreated_at(Playlist.getCreated_at());

            PlaylistDto.add(linkTo(methodOn(PlaylistController.class)
                    .getPlaylistById(PlaylistDto.getId())).withSelfRel());
            PlaylistsList.add(PlaylistDto);
        }

        CollectionModel<PlaylistDto> PlaylistsDto = CollectionModel.of(PlaylistsList);
        PlaylistsDto.add(linkTo(PlaylistController.class).withSelfRel());
        return PlaylistsDto;
    }

    // to entity
    public Playlist toEntity(PlaylistDto PlaylistDto) {
        if (PlaylistDto == null) return null;

        Playlist Playlist = new Playlist();
        Playlist.setId(Playlist.getId());
        Playlist.setMember(memberModelAssembler.toEntity(PlaylistDto.getMember()));
        Playlist.setCreated_at(new Date(System.currentTimeMillis()));
        return Playlist;
    }
}
