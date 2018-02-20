package com.example.froggy.dentistofficetracker;

public class AuthException extends Exception {

    public  AuthException() {}

    public AuthException(String msg){ super(msg); }

    public AuthException(Throwable cause) { super(cause); }

    public AuthException(String msg, Throwable cause) { super(msg, cause); }


}
