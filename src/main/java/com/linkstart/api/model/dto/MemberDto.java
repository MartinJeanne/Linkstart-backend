package com.linkstart.api.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MemberDto {
    private String discordId;
    private String tag;
    private String discordServerId;
    private String avatarURL;
    private LocalDate birthday;
}
