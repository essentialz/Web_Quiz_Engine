package engine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class UserDoesNotOwnQuizException extends RuntimeException {
    public UserDoesNotOwnQuizException(String email, long id) {
        super(String.format("User %s: does not own quiz %d", email, id));
    }
}
