package com.linkstart.api.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @ManyToOne
    private Member member;

    @NotNull
    private String name;

    @NotNull
    private String url;

    private LocalDateTime created_at;

    @Override
    public String toString() {
        return "Playlist: " + name + ", url: " + url;
    }
}
