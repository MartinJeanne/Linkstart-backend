package com.linkstart.api.model.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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