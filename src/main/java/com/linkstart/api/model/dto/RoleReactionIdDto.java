package com.linkstart.api.model.dto;

import com.linkstart.api.model.entity.Message;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RoleReactionIdDto implements Serializable {
    private Message message;
    private String reaction;
}