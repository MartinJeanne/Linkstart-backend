package com.linkstart.backend.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "discordId", nullable=true)
    private int discordId;

    @Override
    public String toString() {
        return "Member: " + this.username + ", discordId: " + this.discordId;
    }
}
