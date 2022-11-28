package com.linkstart.backend.model.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;


@Getter
@Setter
@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Member member;

    @NotNull
    private Long price;

    @NotNull
    private Date created_at;

    @NotNull
    private Date deliver_at;

    @Override
    public String toString() {
        return "Not implemented toString";
    }
}
