package engine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "COMPLETION")
@NoArgsConstructor
@Getter
@Setter
public class Completion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonIgnore
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    private LocalDateTime localDateTime;

    @Builder
    public Completion(Quiz quiz, User user) {
        this.quiz = quiz;
        this.user = user;
        this.localDateTime = LocalDateTime.now();
    }

    @JsonProperty("id")
    public long getQuiz() {
        return quiz.getId();
    }

    @JsonProperty("completedAt")
    public String getLocalDateTime() {
        return localDateTime.toString();
    }
}