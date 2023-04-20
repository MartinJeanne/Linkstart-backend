package com.linkstart.api.model.dto;

import com.linkstart.api.model.entity.DiscordUser;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class QuizDto {
    private Long id;
    private DiscordUserDto discordUserDto;
    private String name;
    private Date created_at;
}
