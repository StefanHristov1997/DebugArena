package com.debugArena.exeption;

public class ObjectNotFoundException extends RuntimeException {

    String message;

    public ObjectNotFoundException(String message) {
        super(message);
    }
}
