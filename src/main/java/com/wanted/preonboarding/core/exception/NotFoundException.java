package com.wanted.preonboarding.core.exception;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String type){
        super(type+"을(를) 찾을 수 없습니다");
    }
}
