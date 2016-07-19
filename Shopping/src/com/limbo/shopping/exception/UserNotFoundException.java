package com.limbo.shopping.exception;

/**
 * Created by Break.D on 7/11/16.
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String s) {
        super(s);
    }
}
