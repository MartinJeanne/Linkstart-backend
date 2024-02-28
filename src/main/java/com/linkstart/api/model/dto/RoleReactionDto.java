package com.linkstart.api.model.dto;

import com.linkstart.api.model.entity.RoleReactionId;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleReactionDto {
    private RoleReactionId roleReactionId;
    private String role;
}
