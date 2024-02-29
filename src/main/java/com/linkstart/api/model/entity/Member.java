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
    @Column(columnDefinition = "VARCHAR(18)")
    private String id;

    @NotNull
    private String tag;

    @NotNull
    @ManyToOne
    private Guild guild;

    private LocalDate birthday;

    private String avatar;

    @Override
    public String toString() {
        return "Member: " + this.tag + ", id: " + this.id;
    }
}
