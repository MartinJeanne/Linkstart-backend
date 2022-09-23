package com.linkstart.backend.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String username;

    private int discordId;

    public Member() {
    }

    public Member(String username, int discordId) {
        this.username = username;
        this.discordId = discordId;
    }

    @Override
    public String toString() {
        return "Member: " + this.username + ", discordId: " + this.discordId;
    }
}
