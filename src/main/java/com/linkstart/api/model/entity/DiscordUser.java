package com.linkstart.api.model.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class DiscordUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String discordId;
    @NotNull
    private String tag;

    @Override
    public String toString() {
        return "Member: " + this.tag + ", discordId: " + this.id;
    }
}
