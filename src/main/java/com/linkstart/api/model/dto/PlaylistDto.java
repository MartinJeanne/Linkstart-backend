package com.linkstart.api.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PlaylistDto {
    private int id;
    private String name;
    private String url;
    private String memberId;
    private LocalDateTime created_at;
}
