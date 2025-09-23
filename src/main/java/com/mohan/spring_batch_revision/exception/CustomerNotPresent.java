package com.mohan.spring_batch_revision.exception;

public class CustomerNotPresent extends RuntimeException{

    public CustomerNotPresent(String message) {
        super(message);
    }
}
