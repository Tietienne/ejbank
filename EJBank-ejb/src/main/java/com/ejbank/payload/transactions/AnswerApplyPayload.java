package com.ejbank.payload.transactions;

public class AnswerApplyPayload {
    private final Boolean result;
    private final String message;

    public AnswerApplyPayload(Boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public Boolean getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }
}
