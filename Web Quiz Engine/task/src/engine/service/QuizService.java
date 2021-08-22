package engine.service;

import engine.exception.QuizNotFoundException;
import engine.exception.UserDoesNotOwnQuizException;
import engine.model.Quiz;
import engine.model.User;
import engine.model.dto.AnswerDto;
import engine.model.dto.AnswerResponseDto;
import engine.repository.QuizRepository;
import engine.security.IPrincipalFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class QuizService {
    private final QuizRepository repository;
    private final UserService userService;
    private final CompletionService completionService;
    private final IPrincipalFacade principalFacade;


    @Autowired
    public QuizService(QuizRepository repository, UserService userService,
                       CompletionService completionService, IPrincipalFacade principalFacade) {
        this.repository = repository;
        this.userService = userService;
        this.completionService = completionService;
        this.principalFacade = principalFacade;
    }

    public Page<Quiz> findAll(Integer page, Integer size, String sortBy) {
        return repository.findAll(
                PageRequest.of(page, size, Sort.by(sortBy).ascending())
        );
    }

    public Quiz findById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException(id));
    }

    public Quiz save(Quiz quiz) {
        User currentUser = userService.findByEmail(principalFacade.getPrincipal().getEmail());

        return repository.save(Quiz.builder()
                .title(quiz.getTitle())
                .text(quiz.getText())
                .options(quiz.getOptions())
                .answer(quiz.getAnswer())
                .createdBy(currentUser)
                .build());
    }

    public void delete(long id) {
        Quiz quiz = findById(id);
        String currentUserEmail = principalFacade.getPrincipal().getEmail();

        if (!quiz.getCreatedBy().getEmail().equals(currentUserEmail)) {
            throw new UserDoesNotOwnQuizException(currentUserEmail, id);
        }

        repository.delete(quiz);
    }

    public AnswerResponseDto solve(long id, AnswerDto answer) {
        AnswerResponseDto response = AnswerResponseDto.builder()
                .success(findById(id).getAnswer().equals(answer.getAnswer()))
                .build();

        if (response.isSuccess()) {
            User currentUser = userService.findByEmail(principalFacade.getPrincipal().getEmail());

            completionService.save(currentUser, findById(id));
        }

        return response;
    }
}