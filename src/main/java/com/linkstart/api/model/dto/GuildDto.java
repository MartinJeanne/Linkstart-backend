package com.linkstart.api.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuildDto {
    private String discordId;
    private String botChannelId;
    private String name;
}
