package com.linkstart.api.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Guild {

    @Id
    @Column(columnDefinition = "VARCHAR(18)")
    private String id;

    @NotNull
    private String name;

    private String botChannelId;

    @Override
    public String toString() {
        return "Guild: " + this.name + ", id: " + this.id;
    }
}
