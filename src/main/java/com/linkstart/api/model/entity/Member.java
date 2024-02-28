package com.linkstart.api.model.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Member {

    @Id
    private long id;

    @NotNull
    private String discordId;

    @NotNull
    private String tag;

    @NotNull
    @ManyToOne
    private Guild guild;

    private LocalDate birthday;

    private String avatar;

    @Override
    public String toString() {
        return "Member: " + this.tag + ", discordId: " + this.id;
    }
}
