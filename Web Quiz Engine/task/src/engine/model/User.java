package engine.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "USER")
@Getter
@Setter
public class User {

    @Id
    @NotBlank(message = "Email address is required!")
    @Pattern(regexp = "^\\w[\\w.]+@\\w+\\.\\w+$", message = "Invalid format!")
    private String email;

    @NotBlank (message = "Password must be at least 5 characters in length")
    @Size(min = 5, message = "Password must be at least 5 characters in length.")
    private String password;

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Quiz> quizzes;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final Set<Completion> completions;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String roles;

    @SuppressWarnings("unused")
    public User() {
        this.quizzes = new HashSet<>();
        this.completions = new HashSet<>();
    }

    @Builder
    public User(String email, String password, String roles) {
        this.email = email;
        this.password = password;
        this.quizzes = new HashSet<>();
        this.completions = new HashSet<>();
        this.roles = roles;
    }
}