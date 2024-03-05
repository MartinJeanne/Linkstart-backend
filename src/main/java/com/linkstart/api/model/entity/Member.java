package com.linkstart.api.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
public class Member {

    @Id
    @Column(columnDefinition = "VARCHAR(18)")
    private String id;

    @NotNull
    private String tag;

    @NotNull
    @ManyToMany
    @JoinTable(name = "member_guilds",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "guild_id"))
    private List<Guild> guilds;

    private LocalDate birthday;

    private String avatar;

    public void addGuild(Guild guild) {
        List<Guild> guilds = this.getGuilds();
        guilds.add(guild);
        this.setGuilds(guilds);
    }

    @Override
    public String toString() {
        return "Member: " + this.tag + ", id: " + this.id;
    }
}
