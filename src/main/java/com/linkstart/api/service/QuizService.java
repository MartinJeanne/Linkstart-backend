package com.linkstart.api.service;

import com.linkstart.api.exception.NoContentException;
import com.linkstart.api.model.dto.DiscordUserDto;
import com.linkstart.api.model.dto.PlaylistDto;
import com.linkstart.api.model.dto.QuizDto;
import com.linkstart.api.model.entity.DiscordUser;
import com.linkstart.api.model.entity.Quiz;
import com.linkstart.api.repo.DiscordUserRepo;
import com.linkstart.api.repo.QuizRepo;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    private final QuizRepo quizRepo;
    private final DiscordUserRepo discordUserRepo;
    private final ModelMapper modelMapper;

    public QuizService(QuizRepo quizRepo,
            DiscordUserRepo discordUserRepo,
            ModelMapper modelMapper) {
        this.quizRepo = quizRepo;
        this.discordUserRepo = discordUserRepo;
        this.modelMapper = modelMapper;
    }

    public List<QuizDto> getQuizzes() {
        List<Quiz> quizzes = quizRepo.findAll();

        return quizzes
                .stream()
                .map(quiz -> {
                    QuizDto q = modelMapper.map(quiz, QuizDto.class);
                    q.setDiscordUserDto(modelMapper.map(quiz.getDiscordUser(), DiscordUserDto.class));
                    return q;
                })
                .toList();
    }

    public QuizDto getQuizById(Long id) {
        Quiz quiz = quizRepo.findById(id).orElseThrow(NoContentException::new);
        QuizDto quizDto = modelMapper.map(quiz, QuizDto.class);
        quizDto.setDiscordUserDto(modelMapper.map(quiz.getDiscordUser(), DiscordUserDto.class));
        return quizDto;
    }

    public QuizDto createQuiz(QuizDto quizDto, Long discordUserId) {
        DiscordUser discordUser = discordUserRepo.findById(discordUserId).orElseThrow(NoContentException::new);
        quizDto.setDiscordUserDto(modelMapper.map(discordUser, DiscordUserDto.class));
        Quiz quiz = modelMapper.map(quizDto, Quiz.class);
        quizRepo.save(quiz);
        return modelMapper.map(quiz, QuizDto.class);
    }

    public QuizDto updateQuiz(Long id, QuizDto quizDto) {
        quizRepo.findById(id).orElseThrow(NoContentException::new);
        quizDto.setId(id);
        Quiz updatedQuiz = quizRepo.save(modelMapper.map(quizDto, Quiz.class));
        return modelMapper.map(updatedQuiz, QuizDto.class);
    }

    public HttpStatus deleteQuiz(Long id) {
        Quiz quiz = quizRepo.findById(id).orElseThrow(NoContentException::new);
        quizRepo.delete(quiz);
        return HttpStatus.OK;
    }

    public List<QuizDto> getQuizByDiscordUser(DiscordUser discordUser) {
        List<Quiz> quizzes = quizRepo.findByDiscordUser(discordUser);
        return quizzes
                .stream()
                .map(quiz -> {
                    QuizDto q = modelMapper.map(quiz, QuizDto.class);
                    q.setDiscordUserDto(modelMapper.map(quiz.getDiscordUser(), DiscordUserDto.class));
                    return q;
                })
                .toList();
    }
}
