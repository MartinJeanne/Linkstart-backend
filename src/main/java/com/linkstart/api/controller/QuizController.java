package com.linkstart.api.controller;

import com.linkstart.api.model.dto.PlaylistDto;
import com.linkstart.api.model.dto.QuizDto;
import com.linkstart.api.model.entity.Quiz;
import com.linkstart.api.service.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public ResponseEntity<List<QuizDto>> getQuizzes() {
        return ResponseEntity.ok(quizService.getQuizzes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizDto> getQuizById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(quizService.getQuizById(id));
    }

    @PostMapping
    public ResponseEntity<QuizDto> createQuiz(@RequestBody QuizDto playlistDto, @RequestParam Long discordUserId) {
        QuizDto quizDto = quizService.createQuiz(playlistDto, discordUserId);
        return ResponseEntity.status(HttpStatus.CREATED).body(quizDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuizDto> updateQuiz(@PathVariable("id") Long id, @RequestBody QuizDto playlistDto) {
        QuizDto quizDto = quizService.updateQuiz(id, playlistDto);
        return  ResponseEntity.ok(quizDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteQuiz(@PathVariable("id") Long id) {
        return ResponseEntity.status(quizService.deleteQuiz(id)).build();
    }
}
