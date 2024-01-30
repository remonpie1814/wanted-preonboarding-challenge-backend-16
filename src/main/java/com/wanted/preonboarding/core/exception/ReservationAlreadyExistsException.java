package com.wanted.preonboarding.core.exception;

public class ReservationAlreadyExistsException extends RuntimeException{
    public ReservationAlreadyExistsException(){
        super("이미 예약된 좌석입니다");
    }
}
