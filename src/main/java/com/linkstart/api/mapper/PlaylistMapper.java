package com.linkstart.api.mapper;

import com.linkstart.api.model.dto.MemberDto;
import com.linkstart.api.model.dto.PlaylistDto;
import com.linkstart.api.model.entity.Guild;
import com.linkstart.api.model.entity.Member;
import com.linkstart.api.model.entity.Playlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class PlaylistMapper {

    public abstract PlaylistDto toDto(Playlist playlist);

    public List<PlaylistDto> toDtoList(List<Playlist> playlists) {
        List<PlaylistDto> playlistsDto = new ArrayList<>();
        for(Playlist playlist : playlists) {
            playlistsDto.add(this.toDto(playlist));
        }
        return playlistsDto;
    }
    public abstract Playlist toEntity(PlaylistDto playlistDto);
}
