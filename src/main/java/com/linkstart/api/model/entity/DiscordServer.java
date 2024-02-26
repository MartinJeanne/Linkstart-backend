package com.linkstart.api.model.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class DiscordServer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String discordId;

    private String botChannelId;

    @NotNull
    private String name;

    @Override
    public String toString() {
        return "Server: " + this.name + ", discordId: " + this.id;
    }
}
