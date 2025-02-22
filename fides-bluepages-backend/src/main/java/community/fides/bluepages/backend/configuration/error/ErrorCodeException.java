package community.fides.bluepages.backend.configuration.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public class ErrorCodeException extends RuntimeException {

    public static final String ERR_RESOURCE_NOT_FOUND = "ERR-RESOURCE-NOT-FOUND";

    private final String errorCode;
    private final HttpStatus httpStatus;

    public ErrorCodeException(final String errorCode, final String message) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public ErrorCodeException(final String errorCode, final String message, final HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return "[" + errorCode + "] " + super.getMessage();
    }
}
