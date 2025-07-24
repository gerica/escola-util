package com.escola.util.security;

// Crie uma exceção customizada para melhor tratamento de erros
public class EmailSendingException extends RuntimeException {
    public EmailSendingException(String message, Throwable cause) {
        super(message, cause);
    }
}