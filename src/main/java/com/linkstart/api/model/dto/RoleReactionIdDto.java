package com.linkstart.api.model.dto;

import com.linkstart.api.model.entity.DiscordMessage;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Getter
@Setter
public class RoleReactionIdDto implements Serializable {
    private DiscordMessage discordMessage;
    private String reaction;
}