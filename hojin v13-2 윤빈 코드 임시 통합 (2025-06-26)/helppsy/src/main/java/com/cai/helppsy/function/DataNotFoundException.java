package com.cai.helppsy.function;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found")
public class DataNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public DataNotFoundException(String message){
        super(message);
    }
}

// 서비스에서 예외 처리를 넘기기 위해 만듦. 현재 사용 안함.
