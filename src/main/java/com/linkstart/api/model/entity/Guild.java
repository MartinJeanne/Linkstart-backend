package com.linkstart.api.model.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Guild {

    @Id
    private long id;

    private String botChannelId;

    @NotNull
    private String name;

    @Override
    public String toString() {
        return "Guild: " + this.name + ", id: " + this.id;
    }
}
