package com.effective.maple.global.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String getErrorCode();
    String getErrorMessage();
    HttpStatus getStatus();
}
