package engine.service;

import engine.model.Completion;
import engine.model.Quiz;
import engine.model.User;
import engine.repository.CompletionRepository;
import engine.security.IPrincipalFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CompletionService {
    private final CompletionRepository repository;
    private final IPrincipalFacade principalFacade;

    @Autowired
    public CompletionService(CompletionRepository repository, IPrincipalFacade principalFacade) {
        this.repository = repository;
        this.principalFacade = principalFacade;
    }

    public void save(User user, Quiz quiz) {
        repository.save(Completion.builder()
                .user(user)
                .quiz(quiz)
                .build()
        );
    }

    public Page<Completion> getCompleted(Integer pageNumber, Integer pageSize, String sortBy) {
        String currentUserEmail = principalFacade.getPrincipal().getEmail();
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());

        return repository.findAllByUserEmail(currentUserEmail, paging);
    }
}
