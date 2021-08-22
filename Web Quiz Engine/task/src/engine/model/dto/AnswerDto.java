package engine.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.HashSet;

@Getter
@Setter
public class AnswerDto {

    @NotNull(message = "Answers must not be null, use an empty array '[]' for no Answer")
    private HashSet<Integer> answer;
}