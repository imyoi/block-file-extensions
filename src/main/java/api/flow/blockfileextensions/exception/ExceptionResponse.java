package api.flow.blockfileextensions.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class ExceptionResponse {

    private final int status;
    private final String code;
    private final String message;

    public static ExceptionResponse of(ERROR error) {
        return ExceptionResponse.builder()
                .status(error.getStatus())
                .code(error.getCode())
                .message(error.getMessage())
                .build();
    }
}
