package com.ejbank.payload.transactions;

public class AnswerValidationPayload {
    private final Boolean result;
    private final String message;
    private final String error;

    public AnswerValidationPayload(Boolean result, String message, String error) {
        this.result = result;
        this.message = message;
        this.error = error;
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
