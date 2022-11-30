package com.ejbank.payload.transactions;

import java.util.Objects;

public class AnswerValidationPayload {
    private final Boolean result;
    private final String message;
    private final String error;

    public AnswerValidationPayload(Boolean result, String message) {
        Objects.requireNonNull(result);
        Objects.requireNonNull(message);
        this.result = result;
        this.message = message;
        this.error = null;
    }

    public Boolean getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }
}
