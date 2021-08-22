package engine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "QUIZ")
@Getter
@Setter
@JsonPropertyOrder({
        "id",
        "title",
        "text",
        "options"
})
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Text is required")
    private String text;

    @NotEmpty(message = "There must be at least 2 options")
    @Size(min = 2, message = "There must be at least 2 options")
    private LinkedHashSet<String> options;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private HashSet<Integer> answer;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private final Set<Completion> completions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User createdBy;

    @SuppressWarnings("unused")
    public Quiz() {
        this.completions = new HashSet<>();
    }

    @Builder
    public Quiz(String title, String text, LinkedHashSet<String> options, HashSet<Integer> answer, User createdBy) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = Optional.ofNullable(answer).isPresent() ? answer : new HashSet<>();
        this.createdBy = createdBy;
        this.completions = new HashSet<>();
    }
}