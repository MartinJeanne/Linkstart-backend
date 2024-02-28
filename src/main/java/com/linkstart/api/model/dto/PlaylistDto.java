package com.linkstart.api.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class PlaylistDto {
    private String name;
    private String url;
    private MemberDto memberDto;
    private Date created_at;
}
