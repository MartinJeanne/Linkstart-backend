package com.linkstart.api.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuildDto {
    private long id;
    private String botChannelId;
    private String name;
}
