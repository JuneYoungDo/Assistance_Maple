package com.effective.maple.global.exception.errorCode;

import com.effective.maple.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CharacterErrorCode implements ErrorCode {
    SERVER_ERROR("SERVER_ERROR","오류가 발생하였거나, 일일 호출한도를 초과하였습니다.",HttpStatus.BAD_REQUEST),
    INVALID_NICKNAME("INVALID_NICKNAME","캐릭터 정보가 없거나, 일일 호출한도를 초과하였습니다.",HttpStatus.BAD_REQUEST),
    INVALID_JOB("INVALID_JOB","직업 정보가 명확하지 않거나, 일일 호출한도를 초과하였습니다.", HttpStatus.BAD_REQUEST),
    INVALID_NICKNAME_INPUT("INVALID_NICKNAME_INPUT","올바르지 않은 닉네임입니다",HttpStatus.BAD_REQUEST),
    ;


    private final String errorCode;
    private final String errorMessage;
    private final HttpStatus status;
}
