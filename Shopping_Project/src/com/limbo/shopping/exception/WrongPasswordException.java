package com.limbo.shopping.exception;

/**
 * Created by Break.D on 7/11/16.
 */
public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException(String s) {
        super(s);
    }
}
