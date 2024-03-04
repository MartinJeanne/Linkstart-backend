package com.linkstart.api.model.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class RoleReactionId implements Serializable {


    @NotNull
    @ManyToOne
    private Message message;

    @NotNull
    private String reaction;
}