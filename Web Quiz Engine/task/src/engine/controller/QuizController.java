package engine.controller;

import engine.model.Quiz;
import engine.model.dto.AnswerDto;
import engine.model.dto.AnswerResponseDto;
import engine.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api")
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/quizzes")
    public Page<Quiz> getQuizzes(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "id") String sortBy) {
        return quizService.findAll(page, size, sortBy);
    }

    @GetMapping("/quizzes/{id}")
    public Quiz getQuiz(@PathVariable @Min(0) int id) {
        return quizService.findById(id);
    }

    @PostMapping("/quizzes")
    public Quiz saveQuiz(@RequestBody @Valid Quiz quiz) {
        return quizService.save(quiz);
    }

    @PostMapping("/quizzes/{id}/solve")
    public AnswerResponseDto solveQuiz(@PathVariable @Min(0) int id,
                                       @RequestBody @Valid AnswerDto answer) {
        return quizService.solve(id, answer);
    }

    @DeleteMapping("/quizzes/{id}")
    public void deleteQuiz(@PathVariable @Min(0) int id, HttpServletResponse response) {
        quizService.delete(id);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}