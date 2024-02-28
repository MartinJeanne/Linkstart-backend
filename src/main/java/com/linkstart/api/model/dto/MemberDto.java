package com.linkstart.api.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MemberDto {
    private long id;
    private String tag;
    private String guildId;
    private String avatar;
    private LocalDate birthday;
}
