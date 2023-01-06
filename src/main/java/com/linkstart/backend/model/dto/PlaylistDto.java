package com.linkstart.backend.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.sql.Date;

@Getter
@Setter
public class PlaylistDto extends RepresentationModel<PlaylistDto> {
    private Long id;
    private String name;
    private String url;
    private DiscordUserDto discordUserDto;
    private Date created_at;
}
