package com.linkstart.api.model.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
public class DiscordUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String discordId;
    @NotNull
    private String tag;
    private LocalDate birthday;

    private String avatarURL;

    @Override
    public String toString() {
        return "Member: " + this.tag + ", discordId: " + this.id;
    }
}
