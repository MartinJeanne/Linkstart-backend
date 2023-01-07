package com.linkstart.api.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class DiscordUserDto extends RepresentationModel<DiscordUserDto> {
    private Long id;
    private String discordId;
    private String tag;
}
