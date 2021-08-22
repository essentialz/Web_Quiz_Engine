package engine.controller;

import engine.model.Completion;
import engine.service.CompletionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CompletionController {
    private final CompletionService service;

    @Autowired
    public CompletionController(CompletionService service) {
        this.service = service;
    }

    @GetMapping("/quizzes/completed")
    public Page<Completion> getCompleted(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "localDateTime") String sortBy
    ) {
        return service.getCompleted(page, size, sortBy);
    }
}
