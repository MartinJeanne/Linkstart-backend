package com.linkstart.api.model.dto;

import com.linkstart.api.model.entity.Quiz;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizQuestionDto {
    private Long id;
    private QuizDto quizDto;
    private String content;
}
