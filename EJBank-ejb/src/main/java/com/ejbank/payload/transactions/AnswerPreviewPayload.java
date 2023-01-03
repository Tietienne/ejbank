package com.ejbank.payload.transactions;

public class AnswerPreviewPayload {
    private final Boolean result;
    private final Float before;
    private final Float after;
    private final String message;
    private final String error;

    public AnswerPreviewPayload(Boolean result, Float before, Float after, String message, String error) {
        this.result = result;
        this.before = before;
        this.after = after;
        this.message = message;
        this.error = error;
    }

    public Boolean getResult() {
        return result;
    }

    public Float getBefore() {
        return before;
    }

    public Float getAfter() {
        return after;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }
}
