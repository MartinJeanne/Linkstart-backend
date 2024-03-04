package com.linkstart.api.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

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
