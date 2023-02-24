package com.linkstart.api.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class PlaylistDto {
    private Long id;
    private String name;
    private String url;
    private DiscordUserDto discordUserDto;
    private Date created_at;
}
