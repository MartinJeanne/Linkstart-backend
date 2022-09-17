package com.garwalle.discordRestApi.model;

import javax.persistence.*;

@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "honor")
    private int honor;

    public Member() {
    }

    public Member(String username, int honor) {
        this.username = username;
        this.honor = honor;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getHonor() {
        return honor;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setHonor(int honor) {
        this.honor = honor;
    }

    @Override
    public String toString() {
        return "Member : " + this.username + ", honor : " + honor;
    }
}
