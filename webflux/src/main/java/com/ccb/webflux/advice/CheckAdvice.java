package com.ccb.webflux.advice;

import com.ccb.webflux.exception.CheckException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

/**
 * 异常处理切面
 */
@ControllerAdvice
public class CheckAdvice {

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity handleBindException(WebExchangeBindException e) {

        return new ResponseEntity<>(toStr(e), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(CheckException.class)
    public ResponseEntity handleBindException(CheckException e) {

        return new ResponseEntity<>(toStr(e), HttpStatus.BAD_REQUEST);
    }

    private String toStr(CheckException e) {
        return e.getFiledName() + ":不能使用" + e.getFiledValue();
    }

    private String toStr(WebExchangeBindException ex) {

        return ex.getFieldErrors().stream()
                .map(e -> e.getField() + ":" + e.getDefaultMessage())
                // reduce 直接得到的是一个optional
                // 所以需要加一个初始值
                .reduce("", (s1, s2) -> s1 + "\n" + s2);
                // or
                // reduce((s1, s2) -> s1 + "\n" + s2)..orElse("");
    }





}
