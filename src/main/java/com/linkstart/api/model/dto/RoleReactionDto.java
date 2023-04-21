package com.linkstart.api.model.dto;

import com.linkstart.api.model.entity.DiscordMessage;
import com.linkstart.api.model.entity.RoleReactionId;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class RoleReactionDto {
    private RoleReactionId roleReactionId;
    private String role;
}
