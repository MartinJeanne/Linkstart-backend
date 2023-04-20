package com.linkstart.api.model.dto;

import com.linkstart.api.model.entity.Quiz;
import com.linkstart.api.model.entity.QuizQuestion;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizAnswerDto {
    private Long id;
    private QuizDto quizDto;
    private QuizQuestionDto quizQuestionDto;
    private String content;
    private Boolean correct;
}
