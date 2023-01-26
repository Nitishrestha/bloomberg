package com.clusus.Bloomberg.exception_handler;

public class InvalidFileFormat extends RuntimeException {

    public InvalidFileFormat(String message) {
        super(message);
    }
}
