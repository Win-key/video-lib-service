package com.video.lib.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Venkatesh Rajendran
 */

public class OAuth2AuthenticationProcessingException extends AuthenticationException {
    public OAuth2AuthenticationProcessingException(String msg, Throwable t) {
        super(msg, t);
    }

    public OAuth2AuthenticationProcessingException(String msg) {
        super(msg);
    }
}
