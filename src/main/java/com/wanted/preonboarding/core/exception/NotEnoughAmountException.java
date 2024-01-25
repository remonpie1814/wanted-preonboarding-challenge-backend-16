package com.wanted.preonboarding.core.exception;

public class NotEnoughAmountException extends Exception{
    public NotEnoughAmountException(){
        super("잔고가 부족합니다");
    }
}
