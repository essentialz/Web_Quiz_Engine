package engine.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

public class AnswerResponseDto {
    @Getter
    private boolean success;

    @Getter
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String feedback;

    @Builder
    public AnswerResponseDto(boolean success) {
        setSuccess(success);
    }

    public void setSuccess(boolean success) {
        this.success = success;
        this.feedback = success ? "Congratulations, you're right!"
                : "Wrong answer! Please, try again.";
    }
}