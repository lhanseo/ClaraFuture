package com.clara.ClaraFuture.exception;

public class OauthProviderNotFoundException extends RuntimeException {
    public OauthProviderNotFoundException(String message) {
        super(message);
    }
}
