package www.shelleyes.com.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class NotFoundException extends RuntimeException {

    private final HttpStatus code;
    private final String message;

    public NotFoundException(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }
}
