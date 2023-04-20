package com.linkstart.api.model.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@Entity
public class QuizAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Quiz quiz;

    @NotNull
    @ManyToOne
    private QuizQuestion quizQuestion;

    @NotNull
    private String content;

    @NotNull
    private Boolean correct;

    @Override
    public String toString() {
        return "TODO";
    }
}
