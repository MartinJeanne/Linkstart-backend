package com.linkstart.api.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GuildDto {
    private String id;
    private String name;
    private String botChannelId;
    private List<String> membersId;
}
