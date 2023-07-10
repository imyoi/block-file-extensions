package api.flow.blockfileextensions.exception;

import lombok.Getter;

@Getter
public enum ERROR {
    ALREADY_EXIST_EXTENSION(400, "ALREADY_EXIST_EXTENSION", "이미 존재하는 확장자입니다."),
    INPUT_REQUEST_EXCEED(400, "INPUT_REQUEST_EXCEED", "추가 가능한 수를 초과하였습니다. (최대 200개)"),

    NO_INPUT_ID(404, "NO_INPUT_ID","입력된 ID 가 없습니다.");

    private final int status;
    private final String code;
    private final String message;

    ERROR(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}