package engine;

import engine.repository.CompletionRepository;
import engine.repository.QuizRepository;
import engine.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = {
        UserRepository.class,
        QuizRepository.class,
        CompletionRepository.class
})
public class WebQuizEngine {
    public static void main(String[] args) {
        SpringApplication.run(WebQuizEngine.class, args);
    }
}