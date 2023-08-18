package com.example.Final_Project_9team.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum ErrorCode {

    ERROR_BAD_REQUEST(400, "ERR_BAD_REQUEST", "Bad request"),
    ERROR_UNAUTHORIZED(401, "ERR_UNAUTHORIZED", "Unauthorized access"),
    ERROR_FORBIDDEN(403, "ERR_FORBIDDEN", "Access forbidden"),
    ERROR_NOT_FOUND(404, "ERR_NOT_FOUND", "Resource not found"),
    ERROR_METHOD_NOT_ALLOWED(405, "ERR_METHOD_NOT_ALLOWED", "Method not allowed"),
    ERROR_NOT_ACCEPTABLE(406, "ERR_NOT_ACCEPTABLE", "Not acceptable"),
    ERROR_CONFLICT(409, "ERR_CONFLICT", "Conflict with existing resource"),
    ERROR_LENGTH_REQUIRED(411, "ERR_LENGTH_REQUIRED", "Length required"),
    ERROR_PRECONDITION_FAILED(412, "ERR_PRECONDITION_FAILED", "Precondition failed"),
    ERROR_UNSUPPORTED_MEDIA(415, "ERR_UNSUPPORTED_MEDIA", "Unsupported media type"),
    ERROR_TOO_MANY_REQUESTS(429, "ERR_TOO_MANY_REQUESTS", "Too many requests"),
    ERROR_INTERNAL_SERVER(500, "ERR_INTERNAL_SERVER", "Internal server error"),
    ERROR_SERVICE_UNAVAILABLE(503, "ERR_SERVICE_UNAVAILABLE", "Service temporarily unavailable"),
    ERROR_GATEWAY_TIMEOUT(504, "ERR_GATEWAY_TIMEOUT", "Gateway timeout"),
    ERROR_NETWORK_AUTHENTICATION_REQUIRED(511, "ERR_NETWORK_AUTH_REQUIRED", "Network authentication required"),

    // 400 BAD_REQUEST 잘못된 요청
    ALREADY_EXISTED_USERNAME(400, "", "이미 존재하는 닉네임입니다"),
    ALREADY_EXISTED_EMAIL(400, "", "이미 존재하는 이메일입니다"),
    INVALID_PARAMETER(400, "ERR_BAD_REQUEST","파라미터 값을 확인해주세요."),

    // 403 FORBIDDEN 권한이 없는 경우
    USER_NO_AUTH(403, "ERR_FORBIDDEN", "해당 기능에 대해 권한이 없습니다."),

    // 404 NOT_FOUND 잘못된 리소스 접근
    USER_NOT_FOUND(404, "ERR_NOT_FOUND", "존재하지 않는 유저입니다."),
    COMMENT_NOT_FOUND(404, "ERR_NOT_FOUND", "존재하지 않는 댓글입니다."),
    POST_NOT_FOUND(404, "ERR_NOT_FOUND", "존재하지 않는 게시글입니다."),
    INVALID_PASSWORD(404, "ERR_NOT_FOUND", "패스워드가 일치하지 않습니다."),
    SCHEDULE_NOT_FOUND(404, "ERR_NOT_FOUNT", "존재히자 않는 일정입니다."),

    // 500 내부 서버 에러
    INTERNAL_SERVER_ERROR(500, "ERR_INTERNAL_SERVER","서버 에러입니다. 서버 팀으로 문의바랍니다.");

    private final int status;
    private final String code;
    private final String message;
    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
