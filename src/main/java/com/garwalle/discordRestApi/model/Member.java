package com.garwalle.discordRestApi.model;

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

    private int honor;

    public Member() {
    }

    public Member(String username, int honor) {
        this.username = username;
        this.honor = honor;
    }

    @Override
    public String toString() {
        return "Member : " + this.username + ", honor : " + honor;
    }
}
