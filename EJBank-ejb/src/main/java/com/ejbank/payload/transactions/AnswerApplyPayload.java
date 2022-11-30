package com.ejbank.payload.transactions;

import java.util.Objects;

public class AnswerApplyPayload {
    private final Boolean result;
    private final String message;

    public AnswerApplyPayload(Boolean result, String message) {
        Objects.requireNonNull(result);
        Objects.requireNonNull(message);
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
