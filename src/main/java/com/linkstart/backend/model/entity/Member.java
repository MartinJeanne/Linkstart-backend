package com.linkstart.backend.model.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Member {

    @Id
    private Long id;

    @NotNull
    private String tag;

    @Override
    public String toString() {
        return "Member: " + this.tag + ", discordId: " + this.id;
    }
}
