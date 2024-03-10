package com.linkstart.api.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Guild {

    @Id
    private String id;

    @NotNull
    private String name;

    private String botChannelId;

    @NotNull
    @ManyToMany(mappedBy = "guilds")
    private List<Member> members;

    @Override
    public String toString() {
        return "Guild: " + this.name + ", id: " + this.id;
    }
}
