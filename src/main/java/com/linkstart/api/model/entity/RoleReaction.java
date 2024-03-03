package com.linkstart.api.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class RoleReaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private RoleReactionId roleReactionId;

    @NotNull
    private String role;
}