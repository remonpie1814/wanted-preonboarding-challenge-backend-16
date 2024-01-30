package com.wanted.preonboarding.core.exception.handler;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.core.exception.NotEnoughAmountException;
import com.wanted.preonboarding.core.exception.NotFoundException;
import com.wanted.preonboarding.core.exception.ReservationAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 찾는 것이 없음
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseHandler<String>> handleNotFoundException(NotFoundException e){
        return new ResponseEntity<>(ResponseHandler
                .<String>builder()
                .data(e.getMessage())
                .build(),
                HttpStatus.NOT_FOUND);
    }

    // 돈이 부족함
    @ExceptionHandler(NotEnoughAmountException.class)
    public ResponseEntity<ResponseHandler<String>> handleNotEnoughAmountException(NotEnoughAmountException e){
        return new ResponseEntity<>(ResponseHandler
                .<String>builder()
                .data(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    // 이미 예약된 좌석
    @ExceptionHandler(ReservationAlreadyExistsException.class)
    public ResponseEntity<ResponseHandler<String>> handlerReservationAlreadExistsException(ReservationAlreadyExistsException e){
        return new ResponseEntity<>(ResponseHandler
                .<String>builder()
                .data(e.getMessage())
                .build(), HttpStatus.NOT_FOUND);
    }

}
