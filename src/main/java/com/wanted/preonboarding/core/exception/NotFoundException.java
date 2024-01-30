package com.wanted.preonboarding.core.exception;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String type){
        super(type+"is not found");
    }
}
