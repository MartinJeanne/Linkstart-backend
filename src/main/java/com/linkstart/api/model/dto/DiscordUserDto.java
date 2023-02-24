package com.linkstart.api.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscordUserDto {
    private Long id;
    private String discordId;
    private String tag;
}
