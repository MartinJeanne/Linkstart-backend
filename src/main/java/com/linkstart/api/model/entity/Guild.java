package com.linkstart.api.model.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Guild {

    @Id
    @Column(columnDefinition = "VARCHAR(18)")
    private String id;

    @NotNull
    private String name;

    @Override
    public String toString() {
        return "Guild: " + this.name + ", id: " + this.id;
    }
}
