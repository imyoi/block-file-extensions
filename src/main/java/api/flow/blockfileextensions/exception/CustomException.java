package api.flow.blockfileextensions.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ERROR error;

    public CustomException(ERROR error) {
        this.error = error;
    }

    public CustomException(ERROR error, String message) {
        super(error.getMessage());
        this.error = error;
    }
}
