package com.linkstart.api.model.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class RoleReactionId implements Serializable {


    @NotNull
    @ManyToOne
    private DiscordMessage discordMessage;

    @NotNull
    private String reaction;
}