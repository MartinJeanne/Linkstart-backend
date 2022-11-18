package com.linkstart.backend.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private int discordId;

    @Override
    public String toString() {
        return "Member: " + this.username + ", discordId: " + this.discordId;
    }
}
